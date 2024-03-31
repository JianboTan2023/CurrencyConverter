package com.college.converter.sunlookup.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;




@Entity
public class ChatMessage {
    @ColumnInfo(name="message")
    protected String message;
    @ColumnInfo(name="timeSent")
    protected String timeSent;
    @ColumnInfo(name="isSentButton")
    protected boolean isSentButton;
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    public ChatMessage(String message, String timeSent, boolean isSentButton) {
        this.message = message;
        this.timeSent =timeSent;
        this.isSentButton=isSentButton;
    }

    public String getMessage()
    {
        return this.message;
    }

    public String getTimeSent()
    {
        return this.timeSent;
    }

    public boolean getIsSentButton()
    {
        return this.isSentButton;
    }
}
