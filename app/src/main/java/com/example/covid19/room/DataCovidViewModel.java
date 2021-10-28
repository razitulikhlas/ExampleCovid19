package com.example.covid19.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.google.gson.Gson;

import java.util.List;

public class DataCovidViewModel extends AndroidViewModel {

    private DataCovidRepository dataCovidRepository;
    private LiveData<List<DataCovid>> allDataCovid;
    private LiveData<List<DataCovidBookmark>> allDataCovidBookmark;
    //private MutableLiveData<List<DataCovid>> findData;
    private List<DataCovid> findData;
    private DataCovid dataCovidById;
    private DataCovidBookmark bookmarkById;

    public DataCovidViewModel(@NonNull Application application) {
        super(application);
        dataCovidRepository = new DataCovidRepository(application);
        allDataCovid = dataCovidRepository.getAllDataCovid();
        allDataCovidBookmark = dataCovidRepository.getAllDataCovidBookmark();
    }

    public LiveData<List<DataCovid>> getAllDataCovid() {
        return allDataCovid;
    }
    public LiveData<List<DataCovidBookmark>> getAllDataCovidBookmark() {
        return allDataCovidBookmark;
    }

    public void insertAll(List<DataCovid> dataCovid){
        dataCovidRepository.insertAll(dataCovid);
    }

    public List<DataCovid> findDataCovid(SimpleSQLiteQuery query){
        findData = dataCovidRepository.findDataCovid(query);
        Gson gson = new Gson();
        String hasil = gson.toJson(findData);
        System.out.println("hasil search vm : "+hasil);
        return findData;
    }

    public DataCovid getDataCovidById(int uid){
        dataCovidById = dataCovidRepository.getDataCovidbyId(uid);
        System.out.println("data covid by id "+dataCovidById.continent);
        return dataCovidById;
    }

    public DataCovidBookmark getBookmarkById(int uid){
        bookmarkById = dataCovidRepository.getBookmarkById(uid);
        System.out.println("data bookmark by id "+bookmarkById.continent);
        return bookmarkById;
    }

    public void insertDataCovid(DataCovid dataCovid){
        dataCovidRepository.insertDataCovid(dataCovid);
    }

    public void deleteAllDataCovid(){
        dataCovidRepository.deleteAllDataCovid();
    }

    public void insertDataCovidBookmark(DataCovidBookmark dataCovidBookmark){
        dataCovidRepository.insertDataCovidBookmark(dataCovidBookmark);
    }

    public void deleteDataCovid(DataCovid dataCovid){
        dataCovidRepository.deleteDataCovid(dataCovid);
    }

    public void deleteDataCovidBookmark(DataCovidBookmark dataCovidBookmark){
        dataCovidRepository.deleteDataCovidBookmark(dataCovidBookmark);
    }
}
