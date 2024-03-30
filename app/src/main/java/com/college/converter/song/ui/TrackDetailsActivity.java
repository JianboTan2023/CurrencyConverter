package com.college.converter.song.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.college.converter.R;
import com.college.converter.song.data.Track;
import com.college.converter.song.data.TrackDAO;
import com.college.converter.song.data.TrackDB;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TrackDetailsActivity extends AppCompatActivity {

    private boolean isLiked = false;
    TrackDAO trackDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deezer_song_detail);
        TrackDB db = Room.databaseBuilder(getApplicationContext(), TrackDB.class, "database-track").build();
        trackDAO = db.trackDAO();

        Intent intent = getIntent();
        String album0 = intent.getStringExtra("album");
        String cover0 = intent.getStringExtra("cover_l");
        String cover1 = intent.getStringExtra("cover_m");
        String song0  = intent.getStringExtra("song");
        String duration0 = intent.getStringExtra("duration");
        String id     = intent.getStringExtra("id");
        String rank   = intent.getStringExtra("rank");

        Track track = new Track(id, song0, duration0, cover1, album0, rank);

        ImageView cover = findViewById(R.id.imageView2);
        TextView album = findViewById(R.id.album2);
        TextView song  = findViewById(R.id.title2);
        TextView duration = findViewById(R.id.duration2);

        Picasso.get().load(cover0).into(cover);
        album.setText(album0);
        song.setText(song0);
        duration.setText(duration0);

        Button likeBtn = findViewById(R.id.likeBtn);
        likeBtn.setOnClickListener(clk -> {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                try {
                    trackDAO.insertTrack(track);
                }catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        ImageButton homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(clk -> {
            Intent nextPage = new Intent(TrackDetailsActivity.this, FavoriteSongActivity.class);
            startActivity(nextPage);
        });
    }


}

