package com.college.converter.song.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

        Toolbar toolbar = findViewById(R.id.deezerToolBar);
        setSupportActionBar(toolbar);

        TrackDB db = Room.databaseBuilder(getApplicationContext(), TrackDB.class, "database-track").build();
        trackDAO = db.trackDAO();

        Intent intent = getIntent();
        String album0 = intent.getStringExtra("album");
        String coverL = intent.getStringExtra("cover_l");
        String coverM = intent.getStringExtra("cover_m");
        String song0  = intent.getStringExtra("song");
        String duration0 = intent.getStringExtra("duration");
        String id     = intent.getStringExtra("id");
        String rank   = intent.getStringExtra("rank");

        Track track = new Track(id, song0, duration0, coverM, coverL, album0, rank);

        ImageView cover = findViewById(R.id.imageView2);
        TextView album = findViewById(R.id.album2);
        TextView song  = findViewById(R.id.title2);
        TextView duration = findViewById(R.id.duration2);

        Picasso.get().load(coverM).into(cover);
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
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    });
                }catch (Exception e){

                }
            });

        });

        ImageButton homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(clk -> {
            Intent nextPage = new Intent(TrackDetailsActivity.this, FavoriteSongActivity.class);
            startActivity(nextPage);
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deezer_menu, menu);
        Log.d("D", menu.toString());
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Menu help
        if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder( TrackDetailsActivity.this );
            builder.setMessage("Steps:\n\n" +
                            "1. Type artist name in the editText.\n" +
                            "2. Click on the artist you want.\n" +
                            "3. Choose a song to see the details.\n" +
                            "4. Add this song to your favorite.\n" +
                            "5. Click on the home button to see your song list.")
                    .setTitle("How to use the Interface?")
                    .setPositiveButton("Ok", (dialog, which) -> {
                    })
                    .create().show();
            // Menu item 2 Help
        } else if (item.getItemId() == R.id.homepage) {
            Intent nextPage = new Intent(TrackDetailsActivity.this, FavoriteSongActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            Intent nextPage = new Intent(TrackDetailsActivity.this, SearchArtistActivity.class);
            startActivity(nextPage);
        }
        else {
        }
        return super.onOptionsItemSelected(item);
    }

}
