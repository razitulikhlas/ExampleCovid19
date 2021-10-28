package com.example.covid19.service.login;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static Client instance;


    public static Client getInstance(){
        if(instance == null)
            instance = new Client();
        return instance;
    }

    public Retrofit builder(String base_url){
       return new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public LoginEndPointInterface service(String base_rl){
        return builder(base_rl).create(LoginEndPointInterface.class);
    }


}
