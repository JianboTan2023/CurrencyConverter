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

import com.college.converter.MainActivity;
import com.college.converter.R;
import com.college.converter.song.data.Track;
import com.college.converter.song.data.TrackDAO;
import com.college.converter.song.data.TrackDB;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
// Activity class to display track details
public class TrackDetailsActivity extends AppCompatActivity {

    private boolean isLiked = false;// Flag to track whether track is liked or not
    TrackDAO trackDAO;// Data Access Object for managing track data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_song_detail);// Set activity layout

        Toolbar toolbar = findViewById(R.id.deezerToolBar);// Find toolbar
        setSupportActionBar(toolbar);// Set toolbar as action bar
        // Initialize Room database and TrackDAO
        TrackDB db = Room.databaseBuilder(getApplicationContext(), TrackDB.class, "database-track").build();
        trackDAO = db.trackDAO();
        // Get track details from intent
        Intent intent = getIntent();
        String album0 = intent.getStringExtra("album");
        String coverL = intent.getStringExtra("cover_l");
        String coverM = intent.getStringExtra("cover_m");
        String song0  = intent.getStringExtra("song");
        String duration0 = intent.getStringExtra("duration");
        String id     = intent.getStringExtra("id");
        String rank   = intent.getStringExtra("rank");
        // Create Track object with received details
        Track track = new Track(id, song0, duration0, coverM, coverL, album0, rank);
        // Find views for displaying track details
        ImageView cover = findViewById(R.id.imageView2);
        TextView album = findViewById(R.id.album2);
        TextView song  = findViewById(R.id.title2);
        TextView duration = findViewById(R.id.duration2);
        // Load track cover image using Picasso library
        Picasso.get().load(coverM).into(cover);
        album.setText(album0);// Set album name
        song.setText(song0);// Set song name
        duration.setText(duration0);// Set song duration
        // Find and set onClickListener for like button
        Button likeBtn = findViewById(R.id.likeBtn);
        boolean result = true;
        likeBtn.setOnClickListener(clk -> {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                try {
                    trackDAO.insertTrack(track);// Insert track into database
                    // Show toast message on UI thread
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    });
                }catch (Exception e){

                }

            });

        });
    }
    // Method to create options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deezer_menu, menu);
        return super.onCreateOptionsMenu(menu);// Return menu creation status
    }
    // Method to handle options menu item selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle menu item selection
        if (item.getItemId() == R.id.help) {
            // Show help dialog
            AlertDialog.Builder builder = new AlertDialog.Builder( TrackDetailsActivity.this );
            builder.setMessage(R.string.deezer_help_info)
                    .setTitle(R.string.deezer_how_to_use_the_interface)

                    .setPositiveButton("Ok", (dialog, which) -> {
                    })
                    .create().show();
            // Menu item 2 Help
        } else if (item.getItemId() == R.id.homepage) {
            // Navigate to MainActivity
            Intent nextPage = new Intent(TrackDetailsActivity.this, MainActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.favoriteSong) {
            // Navigate to FavoriteSongActivity
            Intent nextPage = new Intent(TrackDetailsActivity.this, FavoriteSongActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            // Navigate to FavoriteSongActivity
            Intent nextPage = new Intent(TrackDetailsActivity.this, SearchArtistActivity.class);
            startActivity(nextPage);
        }
        else {// Handle other menu items
        }
        return super.onOptionsItemSelected(item);
    }

}
