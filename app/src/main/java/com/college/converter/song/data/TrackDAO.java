package com.college.converter.song.data;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrackDAO {

    @Insert
    long insertTrack(Track track);
    @Query("Select * from Track")
    List<Track> getAllTracks();
    @Query(("Select * from Track where id = :id"))
    Track getOneTrack(long id);
    @Delete
    void deleteTrack(Track track);
    @Query(("Delete from Track"))
    void deleteAll();
}
