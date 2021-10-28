package com.example.covid19.room;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.covid19.model.Data;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class DataCovidRepository {
    private DataCovidDao dataCovidDao;
    private LiveData<List<DataCovid>> allDataCovid;
    private LiveData<List<DataCovidBookmark>> allDataCovidBookmark;
    private List<DataCovid> dataSearch;
    //private MutableLiveData<List<DataCovid>> findData = new MutableLiveData<>();
    private DataCovid dataCovidbyId;
    private DataCovidBookmark bookmarkById;
    private String status= "empty";

    private final Executor mainThread = new Executor() {
        private Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };

    public DataCovidRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        dataCovidDao = appDatabase.dataCovidDao();
        allDataCovid = dataCovidDao.getAllDataCovid();
        allDataCovidBookmark = dataCovidDao.getAllDataCovidBookmark();
    }

    LiveData<List<DataCovid>> getAllDataCovid() {
        return allDataCovid;
    }

    LiveData<List<DataCovidBookmark>> getAllDataCovidBookmark() {
        return allDataCovidBookmark;
    }

    void insertAll(List<DataCovid> dataCovid){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dataCovidDao.insertAll(dataCovid);
            }
        });
    }

    List<DataCovid> findDataCovid(SimpleSQLiteQuery query){
        status = "empty";

        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dataSearch = dataCovidDao.findByCountry(query);
                Gson gson = new Gson();
                String hasil = gson.toJson(dataSearch);
                System.out.println("hasil search : "+hasil);
                status = "done";
            }
        });

        while (status.equalsIgnoreCase("empty")){
            try {
                AppDatabase.databaseWriteExecutor.awaitTermination(5, TimeUnit.SECONDS);
                System.out.println("process get search waiting");
            }catch (Exception e){
                System.out.println("process get search is error"+e.toString());
            }
        }

        return dataSearch;
    }

    DataCovid getDataCovidbyId(int uid){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dataCovidbyId = dataCovidDao.loadDataCovidByIds(uid);
                System.out.println("data covid by id rep : "+dataCovidbyId.continent);
            }
        });

        while (dataCovidbyId==null){
            try {
                AppDatabase.databaseWriteExecutor.awaitTermination(5, TimeUnit.SECONDS);
                System.out.println("process get data by id is waiting");
            }catch (Exception e){
                System.out.println("process get data by id is error"+e.toString());
            }
        }

        return dataCovidbyId;
    }

    DataCovidBookmark getBookmarkById(int uid){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookmarkById = dataCovidDao.loadBookmarkByIds(uid);
                System.out.println("bookmark by id rep : "+bookmarkById.continent);
            }
        });

        while (bookmarkById==null){
            try {
                AppDatabase.databaseWriteExecutor.awaitTermination(5, TimeUnit.SECONDS);
                System.out.println("process get bookmark by id is waiting");
            }catch (Exception e){
                System.out.println("process get bookmark by id is error"+e.toString());
            }
        }

        return bookmarkById;
    }

    void insertDataCovid(DataCovid dataCovid){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dataCovidDao.insert(dataCovid);
            }
        });
    }

    void insertDataCovidBookmark(DataCovidBookmark bookmark){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dataCovidDao.insertBookmark(bookmark);
            }
        });
    }

    void deleteDataCovid(DataCovid dataCovid){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dataCovidDao.delete(dataCovid);
            }
        });
    }

    void deleteDataCovidBookmark(DataCovidBookmark bookmark){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dataCovidDao.deleteBookmark(bookmark);
                System.out.println("delete bookmark : "+bookmark.country);
            }
        });
    }

    void deleteAllDataCovid(){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dataCovidDao.deleteAllDataCovid();
            }
        });
    }
}
