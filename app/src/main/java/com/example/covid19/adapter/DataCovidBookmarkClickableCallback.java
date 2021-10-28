package com.example.covid19.adapter;

import android.view.View;

import com.example.covid19.room.DataCovid;
import com.example.covid19.room.DataCovidBookmark;

public interface DataCovidBookmarkClickableCallback {
    void onClick(View view, DataCovidBookmark bookmark);
}
