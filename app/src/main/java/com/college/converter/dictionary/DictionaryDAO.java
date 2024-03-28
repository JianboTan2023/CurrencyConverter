package com.college.converter.dictionary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface DictionaryDAO {
    @Insert
    public void insertRecord(DictionaryRecord dr);

    @Query("Select * from DictionaryRecord")
    public List<DictionaryRecord> getAllRecords();

    @Delete
    public void deleteRecord(DictionaryRecord dr);

    @Query("DELETE FROM DictionaryRecord")
    public void deleteAll();
}
