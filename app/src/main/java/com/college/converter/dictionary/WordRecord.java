package com.college.converter.dictionary;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordRecord {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="word")
    protected String word;
    @ColumnInfo(name="definitions")
    protected String definitions;


    public WordRecord(String w, String m){
        word=w;
        definitions =m;
    }

    public WordRecord(){

    }

    public String getWord() {
        return word;
    }

    public String getDefinitions() {
        return definitions;
    }
}
