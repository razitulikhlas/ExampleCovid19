package com.example.covid19.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.covid19.DetailActivity;
import com.example.covid19.MainActivity;
import com.example.covid19.R;
import com.example.covid19.adapter.CaseCovidAdapter;
import com.example.covid19.adapter.DataCovidClickableCallback;
import com.example.covid19.model.CaseCovid;
import com.example.covid19.room.DataCovid;
import com.example.covid19.room.DataCovidViewModel;
import com.google.gson.Gson;

import java.util.List;

public class HomeFragment extends Fragment {

    private LiveData<List<DataCovid>> allDataCovid;
    private DataCovidViewModel dataCovidViewModel;

    private CaseCovidAdapter adapter;
    private RecyclerView recyclerView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.rvDataCovid);
        adapter = new CaseCovidAdapter(callback);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataCovidViewModel.getAllDataCovid().observe(getViewLifecycleOwner(), dataCovids -> {
            adapter.submitList(dataCovids);
        });

    }
}