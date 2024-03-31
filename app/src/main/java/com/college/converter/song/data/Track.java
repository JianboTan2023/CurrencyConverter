package com.college.converter.song.data;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Track")
public class Track {

    @PrimaryKey@NonNull
    String id;
    String title;
    @ColumnInfo(name = "title_short")
    String title_short;
    String title_version;
    @ColumnInfo(name = "duration")
    String duration;
    @ColumnInfo(name = "rank")
    String rank;
    @ColumnInfo(name = "picture_medium")
    String picture_medium;
    @ColumnInfo(name = "picture_big")
    String picture_big;
    @ColumnInfo(name = "album")
    String album;

    public Track() {
    }

    public Track(@NonNull String id, String title_short, String duration, String picture_medium, String picture_big, String album, String rank) {
        this.id = id;
        this.title_short = title_short;
        this.duration = duration;
        this.picture_medium = picture_medium;
        this.picture_big = picture_big;
        this.album = album;
        this.rank = rank;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_short() {
        return title_short;
    }

    public void setTitle_short(String title_short) {
        this.title_short = title_short;
    }

    public String getTitle_version() {
        return title_version;
    }

    public void setTitle_version(String title_version) {
        this.title_version = title_version;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPicture_medium() {
        return picture_medium;
    }

    public void setPicture_medium(String picture_medium) {
        this.picture_medium = picture_medium;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPicture_big() {
        return picture_big;
    }

    public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }
}

