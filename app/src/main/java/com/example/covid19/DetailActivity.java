package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.util.TableInfo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.CartesianSeriesColumn;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAnchor;
import com.anychart.anychart.HoverMode;
import com.anychart.anychart.Pie;
import com.anychart.anychart.Position;
import com.anychart.anychart.TooltipPositionMode;
import com.anychart.anychart.ValueDataEntry;
import com.example.covid19.room.DataCovid;
import com.example.covid19.room.DataCovidBookmark;
import com.example.covid19.room.DataCovidViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends BaseActivity {

    private int idData;
    private DataCovidViewModel dataCovidViewModel;
    private DataCovid dataCovid;

    private TextView tvContinent;
    private TextView tvCountry;
    private TextView tvPopulation;
    private TextView tvTodayCase;
    private TextView tvTotalCase;
    private TextView tvCritical;
    private TextView tvDeath;
    private TextView tvRecovered;
    private Button btnBookmark;
    private AnyChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvContinent = findViewById(R.id.tvContinent);
        tvCountry = findViewById(R.id.tvCountry);
        tvPopulation = findViewById(R.id.tvPopulation);
        tvTodayCase = findViewById(R.id.tvTodayCase);
        tvTotalCase = findViewById(R.id.tvTotalCase);
        tvCritical = findViewById(R.id.tvCritical);
        tvDeath = findViewById(R.id.tvDeath);
        tvRecovered = findViewById(R.id.tvRecovered);
        btnBookmark = findViewById(R.id.btnBookmark);
        //chartView = findViewById(R.id.chartView);

        idData = getIntent().getIntExtra("id_data", 0);
        System.out.println("terima id data : "+idData);
        dataCovidViewModel = new ViewModelProvider(this).get(DataCovidViewModel.class);

        DetailCovidTask task = new DetailCovidTask(idData);
        task.execute();

        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (task.getStatus() == AsyncTask.Status.FINISHED){
                    if (dataCovid==null){
                        Toast.makeText(getApplicationContext(), "Error while getting data, Please Try Again!", Toast.LENGTH_SHORT).show();
                        task.execute();
                    }else{
                        DataCovidBookmark newBookmark = new DataCovidBookmark();
                        newBookmark.uid = dataCovid.uid;
                        newBookmark.country = dataCovid.country;
                        newBookmark.flag = dataCovid.flag;
                        newBookmark.cases = dataCovid.cases;
                        newBookmark.todayCases = dataCovid.todayCases;
                        newBookmark.death = dataCovid.death;
                        newBookmark.recovered = dataCovid.recovered;
                        newBookmark.todayRecovered = dataCovid.recovered;
                        newBookmark.active = dataCovid.active;
                        newBookmark.critical = dataCovid.critical;
                        newBookmark.population = dataCovid.population;
                        newBookmark.continent = dataCovid.continent;

                        InsertBookmarkTask bookmarkTask = new InsertBookmarkTask(newBookmark);
                        bookmarkTask.execute();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Still on progress get data, Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        Pie pie = AnyChart.pie();
//
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new ValueDataEntry("Today Case", 10));
//        data.add(new ValueDataEntry("Total Case", 100));
//        data.add(new ValueDataEntry("Death", 200));
//        data.add(new ValueDataEntry("Recovered", 9));
//
//        pie.setData(data);
//        chartView.setChart(pie);
    }

    private class InsertBookmarkTask extends AsyncTask<DataCovidBookmark, Void, Void>{

        private DataCovidBookmark bookmark;

        public InsertBookmarkTask(DataCovidBookmark bookmark) {
            this.bookmark = bookmark;
        }

        @Override
        protected Void doInBackground(DataCovidBookmark... voids) {
            dataCovidViewModel.insertDataCovidBookmark(bookmark);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Success Add to Bookmark", Toast.LENGTH_SHORT).show();
        }
    }

    private class DetailCovidTask extends AsyncTask<Void, Void, DataCovid>{
        private int uid;

        public DetailCovidTask(int uid) {
            this.uid = uid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected DataCovid doInBackground(Void... voids) {
            dataCovid = dataCovidViewModel.getDataCovidById(uid);

            return dataCovid;
        }

        @Override
        protected void onPostExecute(DataCovid dataCovid) {
            System.out.println("data detail : "+dataCovid.continent);

            String critical = ":   "+dataCovid.critical;
            String todayCase = ":   "+dataCovid.todayCases;
            String totalCase = ":   "+dataCovid.cases;
            String death = ":   "+dataCovid.death;
            String population = ":   "+dataCovid.population;
            String recovered = ":   "+dataCovid.recovered;

            tvContinent.setText(dataCovid.continent);
            tvCountry.setText(dataCovid.country);
            tvCritical.setText(critical);
            tvTodayCase.setText(todayCase);
            tvTotalCase.setText(totalCase);
            tvDeath.setText(death);
            tvPopulation.setText(population);
            tvRecovered.setText(recovered);

//            Cartesian cartesian = AnyChart.column();
//            Pie pie = AnyChart.pie();
//
//            List<DataEntry> data = new ArrayList<>();
//            data.add(new ValueDataEntry("Today Case", dataCovid.todayCases));
//            data.add(new ValueDataEntry("Total Case", dataCovid.cases));
//            data.add(new ValueDataEntry("Death", dataCovid.death));
//            data.add(new ValueDataEntry("Recovered", dataCovid.recovered));
//
//            pie.setData(data);
//            chartView.setChart(pie);

//            CartesianSeriesColumn column = cartesian.column(data);
//
//            column.getTooltip()
//                    .setTitleFormat("{%X}")
//                    .setPosition(Position.CENTER_BOTTOM)
//                    .setAnchor(EnumsAnchor.CENTER_BOTTOM)
//                    .setOffsetX(0d)
//                    .setOffsetY(5d)
//                    .setFormat("${%Value}{groupsSeparator: }");
//
//            cartesian.setAnimation(true);
//            cartesian.setTitle("Data Covid "+dataCovid.country);
//            cartesian.getYScale().setMinimum(0d);
//            cartesian.getYAxis().getLabels().setFormat("${%Value}{groupsSeparator: }");
//            cartesian.getTooltip().setPositionMode(TooltipPositionMode.POINT);
//            cartesian.getInteractivity().setHoverMode(HoverMode.BY_X);
//            cartesian.getXAxis().setTitle("Counts");
//            cartesian.getYAxis().setTitle("Data");
//
//            chartView.setChart(cartesian);
        }
    }


}