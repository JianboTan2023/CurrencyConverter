package com.college.converter.dictionary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * DictionaryDAO, access dictionary records in database
 */
@Dao
public interface DictionaryDAO {
    @Insert
    public void insertRecord(WordRecord dr);

    @Query("Select * from WordRecord")
    public List<WordRecord> getAllRecords();

    @Delete
    public void deleteRecord(WordRecord dr);

    @Query("DELETE FROM WordRecord")
    public void deleteAll();
}
