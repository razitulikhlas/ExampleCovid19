package com.example.covid19.service.login;

import com.example.covid19.model.CaseCovid;
import com.example.covid19.model.UserLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoginEndPointInterface {
    String BASE_URL = "https://talentpool.oneindonesia.id";
    @POST("/api/user/login")
    @FormUrlEncoded
    Call<UserLogin> signIn(
            @Header("X-API-KEY") String apiKey,
            @Header("Content-Type") String contentType,
            @Field("username") String username,
            @Field("password") String password);

    @POST("/api/user/login")
    @FormUrlEncoded
    Call<UserLogin> signInNew(
            @Header("X-API-KEY") String apiKey,
            @Field("username") String username,
            @Field("password") String password);


    @GET("/v2/countries?sort=country")
    Call<List<CaseCovid>> getAllCaseCovid();

    @GET("/v2/countries/{country}")
    Call<CaseCovid> getCaseCovid(@Path("country") String country);

}
