package com.example.covid19.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Query;
import androidx.sqlite.db.SimpleSQLiteQuery;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.covid19.DetailActivity;
import com.example.covid19.R;
import com.example.covid19.adapter.CaseCovidAdapter;
import com.example.covid19.adapter.DataCovidClickableCallback;
import com.example.covid19.model.CaseCovid;
import com.example.covid19.room.DataCovid;
import com.example.covid19.room.DataCovidViewModel;
import com.google.gson.Gson;

import java.util.List;


public class SearchFragment extends Fragment {

    private SearchView searchView;
    private DataCovidViewModel dataCovidViewModel;
    private RecyclerView recyclerView;
    private CaseCovidAdapter adapter;
    private TextView tvSearch;
    private ImageView imgView;
    private TextView tvNotFound;
    private ImageView imgNotFound;

    //private List<DataCovid> dataSearchCovid;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    private final DataCovidClickableCallback callback = new DataCovidClickableCallback() {
        @Override
        public void onClick(View view, DataCovid dataCovid) {
            Intent intent = new Intent(requireActivity(), DetailActivity.class);
            intent.putExtra("id_data", dataCovid.uid);
            requireActivity().startActivity(intent);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataCovidViewModel = new ViewModelProvider(requireActivity()).get(DataCovidViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        imgView = view.findViewById(R.id.imgSearch);
        tvSearch = view.findViewById(R.id.tvSearch);
        imgNotFound = view.findViewById(R.id.imgSearch2);
        tvNotFound = view.findViewById(R.id.tvSearch2);

        recyclerView = view.findViewById(R.id.rvDataSearch);

        searchView = view.findViewById(R.id.searchView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tvNotFound.setVisibility(View.INVISIBLE);
                imgNotFound.setVisibility(View.INVISIBLE);

                System.out.println("submit search : "+query);
                SearchTask task = new SearchTask(query);
                task.execute();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("triggered on query change : "+newText);

                if (TextUtils.isEmpty(newText)){
                    tvNotFound.setVisibility(View.INVISIBLE);
                    imgNotFound.setVisibility(View.INVISIBLE);

                    tvSearch.setVisibility(View.VISIBLE);
                    imgView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                return false;
            }
        });
    }

    private class SearchTask extends AsyncTask<Void, Void, List<DataCovid>>{

        private String search;

        public SearchTask(String search) {
            this.search = search;
        }

        @Override
        protected void onPreExecute() {
            showLoading();
            super.onPreExecute();
        }

        @Override
        protected List<DataCovid> doInBackground(Void... voids) {
            SimpleSQLiteQuery query = new SimpleSQLiteQuery("SELECT * FROM DataCovid WHERE country LIKE '%"+search+"%'");
            System.out.println("yg dicari nya : "+search);

            List<DataCovid> dataSearchCovid;
            dataSearchCovid = dataCovidViewModel.findDataCovid(query);

            return dataSearchCovid;
        }

        @Override
        protected void onPostExecute(List<DataCovid> dataCovids) {

            tvSearch.setVisibility(View.GONE);
            imgView.setVisibility(View.GONE);

            if (dataCovids.isEmpty()){
                tvNotFound.setVisibility(View.VISIBLE);
                imgNotFound.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }else{
                recyclerView.setVisibility(View.VISIBLE);
            }

            Gson gson = new Gson();
            String hasil = gson.toJson(dataCovids);
            System.out.println("hasil search view : "+hasil);

            adapter = new CaseCovidAdapter(callback);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter.submitList(dataCovids);

            hideLoading();
        }
    }

    private void showLoading(){
        DialogFragment fragmentLoading = new LoadingDialogFragment();
        fragmentLoading.show(getParentFragmentManager(), "loading");
    }

    private void hideLoading(){
        Fragment prev = getParentFragmentManager().findFragmentByTag("loading");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }
}