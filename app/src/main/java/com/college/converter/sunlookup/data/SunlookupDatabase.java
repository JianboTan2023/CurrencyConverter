package com.college.converter.sunlookup.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.college.converter.sunlookup.SunlookupDAO;
import com.college.converter.sunlookup.data.SunlookupData;

@Database(entities = {SunlookupData.class}, version=1)
public abstract class SunlookupDatabase extends RoomDatabase {
    public abstract SunlookupDAO slDAO();
}
