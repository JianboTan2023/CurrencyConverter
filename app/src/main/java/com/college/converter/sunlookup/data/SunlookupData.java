package com.college.converter.sunlookup.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class SunlookupData {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="latitude")
    protected String latitude;
    @ColumnInfo(name="longitude")
    protected String longitude;

    public SunlookupData(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude =longitude;
    }

    public String getLatitude()
    {
        return this.latitude;
    }

    public String getLongitude()
    {
        return this.longitude;
    }
}
