package com.example.covid19.service.data_covid;

import com.example.covid19.model.CaseCovid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CaseCovidApiEndPointInterface {
    String BASE_URL = "https://corona.lmao.ninja";

    @GET("/v2/countries?sort=country")
    Call<List<CaseCovid>> getAllCaseCovid();

    @GET("/v2/countries/{country}")
    Call<CaseCovid> getCaseCovid(@Path("country") String country);
}
