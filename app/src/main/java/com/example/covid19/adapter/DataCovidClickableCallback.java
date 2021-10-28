package com.example.covid19.adapter;

import android.view.View;

import com.example.covid19.model.CaseCovid;
import com.example.covid19.room.DataCovid;

public interface DataCovidClickableCallback {
    void onClick(View view, DataCovid dataCovid);
}
