package com.college.converter.sunlookup.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.college.converter.sunlookup.data.ChatMessage;

import java.util.List;


@Dao
public interface ChatMessageDAO {

    @Insert
    public void insertMessage(ChatMessage m);
    @Query("Select * from ChatMessage")
    public List<ChatMessage> getAllMessages();
    @Delete
    public void deleteMessage(ChatMessage m);
}
