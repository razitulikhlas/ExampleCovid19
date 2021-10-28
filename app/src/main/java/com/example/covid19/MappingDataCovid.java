package com.example.covid19;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.example.covid19.model.CaseCovid;
import com.example.covid19.room.DataCovid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MappingDataCovid {
    private int uid;
    private String country;
    private String flag;
    private int cases;
    private int todayCases;
    private int death;
    private int recovered;
    private int todayRecovered;
    private int active;
    private int critical;
    private int population;
    private String continent;

    private List<CaseCovid> input;



    public static List<DataCovid> getMapping(List<CaseCovid> input){
        System.out.println("data input di mapping : "+input.get(1).getCountry());

        List<DataCovid> hasilMapping = new ArrayList<>();

        for (int i=0; i < input.size();i++){
            DataCovid dataCovid = new DataCovid();

            dataCovid.country = input.get(i).getCountry();
            dataCovid.flag = input.get(i).getCountryInfo().getFlag();
            dataCovid.cases = input.get(i).getCases();
            dataCovid.todayCases = input.get(i).getTodayCases();
            dataCovid.death = input.get(i).getDeaths();
            dataCovid.recovered = input.get(i).getRecovered();
            dataCovid.todayRecovered = input.get(i).getTodayRecovered();
            dataCovid.active = input.get(i).getActive();
            dataCovid.critical = input.get(i).getCritical();
            dataCovid.population = input.get(i).getPopulation();
            dataCovid.continent = input.get(i).getContinent();

            hasilMapping.add(dataCovid);
        }

        return hasilMapping;
    }
}
