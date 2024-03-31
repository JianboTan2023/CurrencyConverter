package com.college.converter.sunlookup.data;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class SunlookupData {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="latitude")
    protected String latitude;
    @ColumnInfo(name="longitude")
    protected String longitude;

    public SunlookupData(String lat, String longitude) {
        this.latitude = lat;
        this.longitude =longitude;
    }

    public String getLat()
    {
        return this.latitude;
    }

    public String getLongitude()
    {
        return this.longitude;
    }
}
