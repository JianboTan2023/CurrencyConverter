package com.college.converter.dictionary;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DictionaryRecord {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="word")
    protected String word;
    @ColumnInfo(name="meaning")
    protected String meaning;


    public DictionaryRecord(String w, String m){
        word=w;
        meaning=m;
    }

    public DictionaryRecord(){

    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }
}
