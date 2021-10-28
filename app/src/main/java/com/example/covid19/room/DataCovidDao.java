package com.example.covid19.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.util.List;

@Dao
public interface DataCovidDao {

    @Query("SELECT * FROM DataCovid")
    LiveData<List<DataCovid>> getAllDataCovid();

    @Query("SELECT * FROM DataCovid WHERE uid LIKE :userIds")
    DataCovid loadDataCovidByIds(int userIds);

    @RawQuery
    List<DataCovid> findByCountry(SimpleSQLiteQuery query);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DataCovid dataCovid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<DataCovid> dataCovid);

    @Update
    void update(DataCovid dataCovid);

    @Delete
    void delete(DataCovid dataCovid);

    @Query("DELETE FROM DataCovid")
    void deleteAllDataCovid();


    @Query("SELECT * FROM DataCovidBookmark")
    LiveData<List<DataCovidBookmark>> getAllDataCovidBookmark();

    @Query("SELECT * FROM DataCovidBookmark WHERE uid LIKE :userIds")
    DataCovidBookmark loadBookmarkByIds(int userIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookmark(DataCovidBookmark bookmark);

    @Update
    void updateBookmark(DataCovidBookmark bookmark);

    @Delete
    void deleteBookmark(DataCovidBookmark bookmark);
}
