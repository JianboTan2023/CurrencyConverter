package com.college.converter.song.ui;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.college.converter.R;
import com.college.converter.song.adapter.FavoriteSong_Adapter;
import com.college.converter.song.data.Track;
import com.college.converter.song.data.TrackDAO;
import com.college.converter.song.data.TrackDB;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoriteSongActivity extends AppCompatActivity implements FavoriteSong_Adapter.OnItemClickListener {

    FavoriteSong_Adapter adapter;
    ArrayList<Track> tracks = new ArrayList<>();
    TrackDAO trackDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_favorite_song);

        RecyclerView recyclerView = findViewById(R.id.trackRecyclerView);


        TrackDB db = Room.databaseBuilder(getApplicationContext(), TrackDB.class, "database-track").build();
        trackDAO = db.trackDAO();
        if(tracks.size() == 0)
        {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                tracks.addAll( trackDAO.getAllTracks() ); //Once you get the data from database
                runOnUiThread(() -> {
                    adapter = new FavoriteSong_Adapter(this, tracks);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    // Set the click listener
                    adapter.setOnItemClickListener(this);
                });
            });
        }
    }

    @Override
    public void onItemClick(int position) {
        Track track = tracks.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder( FavoriteSongActivity.this );
        builder.setMessage("Do you want to delete this song: " + track.getTitle_short())
                .setTitle("Question:")
                .setNegativeButton("No", (dialog, which) -> {})
                .setPositiveButton("Yes", (dialog, which) -> {
                    // 4.
                    Track removedTrack = tracks.get(position);
                    Executors.newSingleThreadExecutor().execute(() -> {
                        trackDAO.deleteTrack(removedTrack);
                        runOnUiThread(() -> {
                            tracks.remove(position);
                            adapter.notifyItemRemoved(position);
                            Snackbar.make(findViewById(R.id.trackRecyclerView), "You deleted song #" + track.getId(), Snackbar.LENGTH_LONG)
                                    .setAction("Undo", clk2 -> {
                                        tracks.add(position, removedTrack);
                                        adapter.notifyItemInserted(position);
                                    })
                                    .show();
                        });
                    });
                })
                .create().show();

    }
}

