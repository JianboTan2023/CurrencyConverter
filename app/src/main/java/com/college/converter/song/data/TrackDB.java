package com.college.converter.song.data;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Track.class}, version=1)
public abstract class TrackDB extends RoomDatabase {

    public abstract TrackDAO trackDAO();
}
