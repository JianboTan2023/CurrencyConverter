package com.college.converter.song.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.college.converter.MainActivity;
import com.college.converter.R;
import com.college.converter.song.data.Track;
import com.college.converter.song.data.TrackDAO;
import com.college.converter.song.data.TrackDB;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
// Activity class to display favorite songs
public class FavoriteSongActivity extends AppCompatActivity{

    FavoriteSongRecyclerViewAdapter adapter;// Adapter for RecyclerView
    ArrayList<Track> tracks = new ArrayList<>();// List to store favorite tracks
    TrackDAO trackDAO;// Data Access Object for managing track data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_favorite_song);

        Toolbar toolbar = findViewById(R.id.deezerToolBar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.trackRecyclerView);
        // Initialize TrackDB and TrackDAO
        TrackDB db = Room.databaseBuilder(getApplicationContext(), TrackDB.class, "database-track").build();
        trackDAO = db.trackDAO();
        // Check if tracks list is empty, if so, fetch data from database
        if (tracks.size() == 0) {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                // Retrieve tracks from database
                tracks.addAll(trackDAO.getAllTracks()); //Once you get the data from database
                runOnUiThread(() -> {
                    // Retrieve tracks from database
                    adapter = new FavoriteSongRecyclerViewAdapter(this, tracks);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                });
            });
        }
    }
    // Method to create options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deezer_menu, menu);
        Log.d("D", menu.toString());
        return super.onCreateOptionsMenu(menu);
    }
    // Method to handle options menu item selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Menu help
        if (item.getItemId() == R.id.help) {
            // Display help dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteSongActivity.this);
            builder.setMessage(getString(R.string.deezer_help_info))
                    .setTitle(R.string.deezer_how_to_use_the_interface)
                    .setPositiveButton("Ok", (dialog, which) -> {
                    })
                    .create().show();
            // Menu item 2 Help
        } else if (item.getItemId() == R.id.homepage) {
            // Navigate to MainActivity
            Intent nextPage = new Intent(FavoriteSongActivity.this, MainActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.favoriteSong) {
            // Navigate to FavoriteSongActivity
            Intent nextPage = new Intent(FavoriteSongActivity.this, FavoriteSongActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            // Navigate to SearchArtistActivity
            Intent nextPage = new Intent(FavoriteSongActivity.this, SearchArtistActivity.class);
            startActivity(nextPage);
        } else {
        }
        return super.onOptionsItemSelected(item);
    }
    // RecyclerView adapter class for displaying favorite songs
    class FavoriteSongRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteSongRecyclerViewAdapter.FavoriteSongViewRowHolder> {

        private Context context;// Context to access application resources and operations
        private ArrayList<Track> tracks;// List to store favorite tracks
        TrackDAO trackDAO;// Data Access Object for managing track data

        // Constructor to initialize the adapter with context and track list
        public FavoriteSongRecyclerViewAdapter(Context context, ArrayList<Track> tracks) {
            this.context = context;
            this.tracks = tracks;
        }
        // Method to create ViewHolder objects
        @NonNull
        @Override
        public FavoriteSongViewRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.deerzer_favorite_song_recycleview_row, parent, false);

            return new FavoriteSongViewRowHolder(view);
        }
        // Method to bind data to ViewHolder
        @Override
        public void onBindViewHolder(@NonNull FavoriteSongRecyclerViewAdapter.FavoriteSongViewRowHolder holder, int position) {

            Track track = tracks.get(position);
            holder.title.setText(track.getTitle_short());
            holder.rank.setText(track.getRank());
            holder.duration.setText(track.getDuration());
            holder.bind(track);

        }
        // Method to get the total number of items in the dataset
        @Override
        public int getItemCount() {
            return tracks.size();
        }
        // Method to get the total number of items in the dataset
        public class FavoriteSongViewRowHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView title, rank, duration;
            ImageButton deleteBtn;
            // Constructor to initialize view elements
            public FavoriteSongViewRowHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                title = itemView.findViewById(R.id.songTitle);
                rank = itemView.findViewById(R.id.rank);
                duration = itemView.findViewById(R.id.duration);
                deleteBtn = itemView.findViewById(R.id.deleteBtn);
                // Initialize TrackDB and TrackDAO
                TrackDB db = Room.databaseBuilder(context.getApplicationContext(), TrackDB.class, "database-track").build();
                trackDAO = db.trackDAO();
                // Set click listener for delete button
                deleteBtn.setOnClickListener(v -> {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Display confirmation dialog for deleting song
                        AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteSongActivity.this);
                        builder.setMessage(getString(R.string.deezer_do_you_want_to_delete_the_song) + title.getText())

                                .setTitle(R.string.ds_question)
                                .setNegativeButton(R.string.ds_no, (dialog, which) -> {
                                })
                                .setPositiveButton(R.string.ds_yes, (dialog, which) -> {
                                    Track removedTrack = tracks.get(position);// Delete the song from database
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        trackDAO.deleteTrack(removedTrack);
                                        runOnUiThread(() -> {
                                            tracks.remove(position);// Remove song from list and update adapter
                                            adapter.notifyItemRemoved(position);
                                            // Show snackbar with option to undo deletion
                                            Snackbar.make(title, getString(R.string.ds_you_deleted_song) + position, Snackbar.LENGTH_LONG)
                                                    .setAction(R.string.ds_undo, clk2 -> {

                                                        tracks.add(position, removedTrack);
                                                        adapter.notifyItemInserted(position);
                                                        Executors.newSingleThreadExecutor().execute(() -> {
                                                            trackDAO.insertTrack(removedTrack);
                                                        });
                                                    })
                                                    .show();
                                        });
                                    });
                                })
                                .create().show();
                    }
                });

                // Set click listener for item view
                itemView.setOnClickListener(clk -> {
                    int position = getAbsoluteAdapterPosition();
                    Track track = tracks.get(position);
                    // Navigate to TrackDetailsActivity and pass track details
                    Intent nextPage = new Intent(FavoriteSongActivity.this, TrackDetailsActivity.class);
                    nextPage.putExtra("album", track.getAlbum());
                    nextPage.putExtra("cover_l", track.getPicture_big());
                    nextPage.putExtra("cover_m", track.getPicture_medium());
                    nextPage.putExtra("song", track.getTitle_short());
                    nextPage.putExtra("duration", track.getDuration());
                    nextPage.putExtra("id", track.getId());
                    nextPage.putExtra("rank", track.getRank());
                    startActivity(nextPage);
                });
            }
            // Method to bind track data to ImageView
            public void bind(Track track) {
                Picasso.get().load(track.getPicture_medium()).into(imageView);
            }
        }
    }
}