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

import com.college.converter.R;
import com.college.converter.song.data.Track;
import com.college.converter.song.data.TrackDAO;
import com.college.converter.song.data.TrackDB;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoriteSongActivity extends AppCompatActivity{

    FavoriteSongRecyclerViewAdapter adapter;
    ArrayList<Track> tracks = new ArrayList<>();
    TrackDAO trackDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_favorite_song);

        Toolbar toolbar = findViewById(R.id.deezerToolBar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.trackRecyclerView);

        TrackDB db = Room.databaseBuilder(getApplicationContext(), TrackDB.class, "database-track").build();
        trackDAO = db.trackDAO();
        if (tracks.size() == 0) {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                tracks.addAll(trackDAO.getAllTracks()); //Once you get the data from database
                runOnUiThread(() -> {
                    adapter = new FavoriteSongRecyclerViewAdapter(this, tracks);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                });
            });
        }
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
            AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteSongActivity.this);
            builder.setMessage(getString(R.string.deezer_help_info))
                    .setTitle(R.string.deezer_how_to_use_the_interface)
                    .setPositiveButton("Ok", (dialog, which) -> {
                    })
                    .create().show();
            // Menu item 2 Help
        } else if (item.getItemId() == R.id.homepage) {
            Intent nextPage = new Intent(FavoriteSongActivity.this, FavoriteSongActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            Intent nextPage = new Intent(FavoriteSongActivity.this, SearchArtistActivity.class);
            startActivity(nextPage);
        } else {
        }
        return super.onOptionsItemSelected(item);
    }

    class FavoriteSongRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteSongRecyclerViewAdapter.FavoriteSongViewRowHolder> {

        private Context context;
        private ArrayList<Track> tracks;
        TrackDAO trackDAO;

        public FavoriteSongRecyclerViewAdapter(Context context, ArrayList<Track> tracks) {
            this.context = context;
            this.tracks = tracks;
        }

        @NonNull
        @Override
        public FavoriteSongViewRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.deerzer_favorite_song_recycleview_row, parent, false);

            return new FavoriteSongViewRowHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FavoriteSongRecyclerViewAdapter.FavoriteSongViewRowHolder holder, int position) {

            Track track = tracks.get(position);
            holder.title.setText(track.getTitle_short());
            holder.rank.setText(track.getRank());
            holder.duration.setText(track.getDuration());
            holder.bind(track);

        }

        @Override
        public int getItemCount() {
            return tracks.size();
        }

        public class FavoriteSongViewRowHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView title, rank, duration;
            ImageButton deleteBtn;

            public FavoriteSongViewRowHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                title = itemView.findViewById(R.id.songTitle);
                rank = itemView.findViewById(R.id.rank);
                duration = itemView.findViewById(R.id.duration);
                deleteBtn = itemView.findViewById(R.id.deleteBtn);

                TrackDB db = Room.databaseBuilder(context.getApplicationContext(), TrackDB.class, "database-track").build();
                trackDAO = db.trackDAO();
                deleteBtn.setOnClickListener(v -> {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteSongActivity.this);
                        builder.setMessage(getString(R.string.deezer_do_you_want_to_delete_the_song) + title.getText())
                                .setTitle("Question:")
                                .setNegativeButton("No", (dialog, which) -> {
                                })
                                .setPositiveButton("Yes", (dialog, which) -> {
                                    // 4.
                                    Track removedTrack = tracks.get(position);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        trackDAO.deleteTrack(removedTrack);
                                        runOnUiThread(() -> {
                                            tracks.remove(position);
                                            adapter.notifyItemRemoved(position);
                                            Snackbar.make(title, "You deleted song #" + position, Snackbar.LENGTH_LONG)
                                                    .setAction("Undo", clk2 -> {
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

                // Set click listener
                itemView.setOnClickListener(clk -> {
                    int position = getAbsoluteAdapterPosition();
                    Track track = tracks.get(position);
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

            public void bind(Track track) {
                Picasso.get().load(track.getPicture_medium()).into(imageView);
            }
        }
    }
}