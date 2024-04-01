package com.college.converter.sunlookup;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.college.converter.sunlookup.data.SunlookupData;
import java.util.List;

@Dao
public interface SunlookupDAO {

    @Insert
    public void insertData(SunlookupData s);
    @Query("Select * from SunlookupData")
    public List<SunlookupData> getAllData();
    @Delete
    public void deleteData(SunlookupData s);
}
