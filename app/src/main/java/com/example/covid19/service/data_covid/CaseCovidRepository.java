package com.example.covid19.service.data_covid;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.covid19.MappingDataCovid;
import com.example.covid19.model.CaseCovid;
import com.example.covid19.room.AppDatabase;
import com.example.covid19.room.DataCovid;
import com.example.covid19.room.DataCovidDao;
import com.example.covid19.service.login.Client;
import com.example.covid19.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaseCovidRepository {

    private static CaseCovidRepository instance;
    private Client client;
    private DataCovidDao dataCovidDao;
    private AppDatabase appDatabase;


    public static CaseCovidRepository getInstance(Application application) {
        if (instance == null)
            instance = new CaseCovidRepository(application);
        return instance;
    }

    public CaseCovidRepository(Application application) {
        client = Client.getInstance();
        appDatabase = AppDatabase.getDatabase(application);
        dataCovidDao = appDatabase.dataCovidDao();
    }

    public void setData() {
        client.service(Constants.BASE_INFO).getAllCaseCovid().enqueue(new Callback<List<CaseCovid>>() {
            @Override
            public void onResponse(Call<List<CaseCovid>> call, Response<List<CaseCovid>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        appDatabase.getQueryExecutor().execute(() -> dataCovidDao.insertAll(MappingDataCovid.getMapping(response.body())));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CaseCovid>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<DataCovid>> getAllData() {
        return dataCovidDao.getAllDataCovid();
    }


}

