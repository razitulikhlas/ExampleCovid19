package com.example.covid19.service.data_covid;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.covid19.model.CaseCovid;
import com.example.covid19.room.DataCovid;

import java.util.List;

public class CaseCovidViewModel extends AndroidViewModel {

    private CaseCovidRepository caseCovidRepository;
    private MutableLiveData<List<CaseCovid>> dataLiveCaseCovid = new MutableLiveData<>();
    private List<CaseCovid> allCaseCovid;

    public CaseCovidViewModel(@NonNull Application application) {
        super(application);
        caseCovidRepository = CaseCovidRepository.getInstance(application);
    }

    public void callData(){
        caseCovidRepository.setData();
    }
    public LiveData<List<DataCovid>> getData(){
        return caseCovidRepository.getAllData();
    }
//    public MutableLiveData<List<CaseCovid>> getLiveDataCaseCovid(){
//        return dataLiveCaseCovid;
//    }
//
//    public List<CaseCovid> getAllCaseCovid(){
//        return allCaseCovid;
//    }
}
