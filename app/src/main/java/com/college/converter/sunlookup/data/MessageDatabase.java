package com.college.converter.sunlookup.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.college.converter.data.ChatMessage;


@Database(entities = {ChatMessage.class}, version=1)
public abstract class MessageDatabase extends RoomDatabase {

    public abstract ChatMessageDAO cmDAO();
}
