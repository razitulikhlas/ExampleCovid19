package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.anychart.AnyChartView;
import com.example.covid19.room.DataCovid;
import com.example.covid19.room.DataCovidBookmark;
import com.example.covid19.room.DataCovidViewModel;
import com.google.gson.Gson;

public class DetailBookmarkActivity extends BaseActivity {

    private int idData;
    private DataCovidViewModel dataCovidViewModel;
    private DataCovidBookmark dataCovidBookmark;

    private TextView tvContinent;
    private TextView tvCountry;
    private TextView tvPopulation;
    private TextView tvTodayCase;
    private TextView tvTotalCase;
    private TextView tvCritical;
    private TextView tvDeath;
    private TextView tvRecovered;
    private Button btnDelete;
    private AnyChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bookmark);

        Toolbar toolbar = findViewById(R.id.toolbar2);
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

        tvContinent = findViewById(R.id.tvContinentBookmark);
        tvCountry = findViewById(R.id.tvCountryBookmark);
        tvPopulation = findViewById(R.id.tvPopulationBookmark);
        tvTodayCase = findViewById(R.id.tvTodayCaseBookmark);
        tvTotalCase = findViewById(R.id.tvTotalCaseBookmark);
        tvCritical = findViewById(R.id.tvCriticalBookmark);
        tvDeath = findViewById(R.id.tvDeathBookmark);
        tvRecovered = findViewById(R.id.tvRecoveredBookmark);
        btnDelete = findViewById(R.id.btnDelete);
        //chartView = findViewById(R.id.chartViewBookmark);

        idData = getIntent().getIntExtra("id_data_bookmark", 0);
        System.out.println("terima id data : "+idData);
        dataCovidViewModel = new ViewModelProvider(this).get(DataCovidViewModel.class);

        DetailBookmarkTask task = new DetailBookmarkTask(idData);
        task.execute();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (task.getStatus() == AsyncTask.Status.FINISHED){
                    if (dataCovidBookmark==null){
                        Toast.makeText(getApplicationContext(), "Error while getting data, Please Try Again!", Toast.LENGTH_SHORT).show();
                        task.execute();
                    }else{
                        DataCovidBookmark bookmark = new DataCovidBookmark();
                        bookmark.uid = dataCovidBookmark.uid;
                        bookmark.country = dataCovidBookmark.country;
                        bookmark.flag = dataCovidBookmark.flag;
                        bookmark.cases = dataCovidBookmark.cases;
                        bookmark.todayCases = dataCovidBookmark.todayCases;
                        bookmark.death = dataCovidBookmark.death;
                        bookmark.recovered = dataCovidBookmark.recovered;
                        bookmark.todayRecovered = dataCovidBookmark.recovered;
                        bookmark.active = dataCovidBookmark.active;
                        bookmark.critical = dataCovidBookmark.critical;
                        bookmark.population = dataCovidBookmark.population;
                        bookmark.continent = dataCovidBookmark.continent;

                        Gson gson = new Gson();
                        String data = gson.toJson(bookmark);
                        System.out.println("data yg akan dihapus "+data);
                        DeleteBookmarkTask bookmarkTask = new DeleteBookmarkTask(bookmark);
                        bookmarkTask.execute();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Still on progress get data, Please Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class DeleteBookmarkTask extends AsyncTask<DataCovidBookmark, Void, Void>{

        private DataCovidBookmark bookmark;

        public DeleteBookmarkTask(DataCovidBookmark bookmark) {
            this.bookmark = bookmark;
        }

        @Override
        protected Void doInBackground(DataCovidBookmark... voids) {
            dataCovidViewModel.deleteDataCovidBookmark(bookmark);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Success Delete from Bookmark", Toast.LENGTH_SHORT).show();
        }
    }

    private class DetailBookmarkTask extends AsyncTask<Void, Void, DataCovidBookmark>{
        private int uid;

        public DetailBookmarkTask(int uid) {
            this.uid = uid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected DataCovidBookmark doInBackground(Void... voids) {
            dataCovidBookmark = dataCovidViewModel.getBookmarkById(uid);

            return dataCovidBookmark;
        }

        @Override
        protected void onPostExecute(DataCovidBookmark dataCovid) {
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