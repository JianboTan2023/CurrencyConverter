package com.college.converter.dictionary;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WordRecord.class}, version = 1)
public abstract class DictionaryDatabase extends RoomDatabase {
    public abstract DictionaryDAO dicDAO();

}
