package com.example.covid19.service.data_covid;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.covid19.model.CaseCovid;

import java.util.List;

public class CaseCovidViewModel extends AndroidViewModel {

    private CaseCovidRepository caseCovidRepository;
    private MutableLiveData<List<CaseCovid>> dataLiveCaseCovid = new MutableLiveData<>();
    private List<CaseCovid> allCaseCovid;

    public CaseCovidViewModel(@NonNull Application application) {
        super(application);
        caseCovidRepository = new CaseCovidRepository(application);
        allCaseCovid = caseCovidRepository.getCaseCovidFromNetwork();
        //dataLiveCaseCovid.setValue(allCaseCovid);
    }

    public MutableLiveData<List<CaseCovid>> getLiveDataCaseCovid(){
        return dataLiveCaseCovid;
    }

    public List<CaseCovid> getAllCaseCovid(){
        return allCaseCovid;
    }
}
