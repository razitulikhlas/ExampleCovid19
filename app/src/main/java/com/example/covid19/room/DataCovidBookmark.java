package com.example.covid19.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DataCovidBookmark {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "flag")
    public String flag;

    @ColumnInfo(name = "cases")
    public int cases;

    @ColumnInfo(name = "todayCases")
    public int todayCases;

    @ColumnInfo(name = "death")
    public int death;

    @ColumnInfo(name = "recovered")
    public int recovered;

    @ColumnInfo(name = "todayRecovered")
    public int todayRecovered;

    @ColumnInfo(name = "active")
    public int active;

    @ColumnInfo(name = "critical")
    public int critical;

    @ColumnInfo(name = "population")
    public int population;

    @ColumnInfo(name = "continent")
    public String continent;
}
