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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid19.DetailActivity;
import com.example.covid19.DetailBookmarkActivity;
import com.example.covid19.R;
import com.example.covid19.adapter.BookmarkAdapter;
import com.example.covid19.adapter.CaseCovidAdapter;
import com.example.covid19.adapter.DataCovidBookmarkClickableCallback;
import com.example.covid19.adapter.DataCovidClickableCallback;
import com.example.covid19.room.DataCovid;
import com.example.covid19.room.DataCovidBookmark;
import com.example.covid19.room.DataCovidViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookmarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookmarkFragment extends Fragment {

    private LiveData<List<DataCovidBookmark>> allDataCovidBookmark;
    private DataCovidViewModel dataCovidViewModel;
    private BookmarkAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvBookmark;
    private ImageView imgBookmark;

    public static BookmarkFragment newInstance() {
        return new BookmarkFragment();
    }

    private final DataCovidBookmarkClickableCallback callback = new DataCovidBookmarkClickableCallback() {
        @Override
        public void onClick(View view, DataCovidBookmark bookmark) {
            Intent intent = new Intent(requireActivity(), DetailBookmarkActivity.class);
            intent.putExtra("id_data_bookmark", bookmark.uid);
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

        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

        tvBookmark = view.findViewById(R.id.tvNF);
        imgBookmark = view.findViewById(R.id.imgBookmarkNotFound);

        recyclerView = view.findViewById(R.id.rvDataCovidBookmark);
        adapter = new BookmarkAdapter(callback);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataCovidViewModel.getAllDataCovidBookmark().observe(getViewLifecycleOwner(), dataCovids -> {
            if (dataCovids==null || dataCovids.isEmpty()){
                tvBookmark.setVisibility(View.VISIBLE);
                imgBookmark.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }else{
                tvBookmark.setVisibility(View.GONE);
                imgBookmark.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            adapter.submitList(dataCovids);
        });

    }
}