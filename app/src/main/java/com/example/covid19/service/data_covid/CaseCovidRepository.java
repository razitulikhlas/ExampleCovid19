package com.example.covid19.service.data_covid;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import com.example.covid19.model.CaseCovid;
import com.example.covid19.service.RetrofitInstance;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CaseCovidRepository {
    private CaseCovidApiEndPointInterface API;
    private List<CaseCovid> allCaseCovid;
    private String statusGetData = "empty";

    private final ExecutorService networkExecutor =
            Executors.newFixedThreadPool(4);
    private final Executor mainThread = new Executor() {
        private Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };

    public CaseCovidRepository(Application application){
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        retrofitInstance.execCovid();
        API = retrofitInstance.getCovidAPI();
    }

    public List<CaseCovid> getCaseCovidFromNetwork(){
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CaseCovid> caseCovidFromNetwork = API.getAllCaseCovid().execute().body();
                    if (caseCovidFromNetwork == null){
                        statusGetData = "empty";
                    }else{
                        statusGetData = "success";
                        allCaseCovid = caseCovidFromNetwork;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        while (statusGetData.equalsIgnoreCase("empty")){
            try {
                networkExecutor.awaitTermination(5, TimeUnit.SECONDS);
                System.out.println("process get data is waiting");
            }catch (Exception e){
                System.out.println("process get data is error"+e.toString());
            }
        }
        return allCaseCovid;
    }

}

