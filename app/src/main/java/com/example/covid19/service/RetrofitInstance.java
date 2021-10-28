package com.example.covid19.service;

import com.example.covid19.service.data_covid.CaseCovidApiEndPointInterface;
import com.example.covid19.service.login.LoginEndPointInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private LoginEndPointInterface LoginAPI;
    private CaseCovidApiEndPointInterface covidAPI;

    public void execLogin(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofit.create(LoginEndPointInterface.class);

        LoginAPI = retrofit.create(LoginEndPointInterface.class);
    }

    public void execCovid(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(covidAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofit.create(LoginEndPointInterface.class);
        covidAPI = retrofit.create(CaseCovidApiEndPointInterface.class);
    }

    public LoginEndPointInterface getLoginAPI(){
        return LoginAPI;
    }

    public CaseCovidApiEndPointInterface getCovidAPI(){
        return covidAPI;
    }
}
