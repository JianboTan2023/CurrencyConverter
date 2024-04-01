package com.college.converter.song.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.college.converter.MainActivity;
import com.college.converter.R;
import com.college.converter.song.adapter.Track_Adapter;
import com.college.converter.song.data.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
// Activity class to display list of tracks
public class TrackListActivity extends AppCompatActivity implements Track_Adapter.OnItemClickListener {

    Track_Adapter adapter; // Adapter for handling track data
    ArrayList<Track> tracks = new ArrayList<>(); // List to store track objects

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_tracklist); // Set activity layout

        Toolbar toolbar = findViewById(R.id.deezerToolBar); // Find toolbar
        setSupportActionBar(toolbar); // Set toolbar as action bar
        getSupportActionBar().setTitle("Track List"); // Set title for action bar

        RecyclerView recyclerView = findViewById(R.id.trackRecyclerView); // Find RecyclerView
        adapter = new Track_Adapter(this, tracks); // Initialize adapter
        recyclerView.setAdapter(adapter); // Set adapter to RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set layout manager

        Intent intent = getIntent(); // Get intent from previous activity
        String url = intent.getStringExtra("url"); // Get URL from intent
        sendRequest(url); // Send request to fetch track data from URL

        // Set click listener for RecyclerView items
        adapter.setOnItemClickListener(this);
    }
    // Method to handle item click in RecyclerView
    @Override
    public void onItemClick(int position) {
        Track track = tracks.get(position);// Get clicked track object
        // Create intent to start TrackDetailsActivity and pass track details as extras
        Intent nextPage = new Intent(TrackListActivity.this, TrackDetailsActivity.class);
        nextPage.putExtra("album", track.getAlbum());
        nextPage.putExtra("cover_l", track.getPicture_big());
        nextPage.putExtra("cover_m", track.getPicture_medium());
        nextPage.putExtra("song", track.getTitle_short());
        nextPage.putExtra("duration", track.getDuration());
        nextPage.putExtra("id", track.getId());
        nextPage.putExtra("rank", track.getRank());
        startActivity(nextPage);// Start TrackDetailsActivity
    }
    // Method to create options menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deezer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // Method to handle options menu item selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle menu item selection
        if (item.getItemId() == R.id.help) {
            // Show help dialog
            AlertDialog.Builder builder = new AlertDialog.Builder( TrackListActivity.this );
            builder.setMessage(R.string.deezer_help_info)
                    .setTitle(R.string.deezer_how_to_use_the_interface)
                    .setPositiveButton("Ok", (dialog, which) -> {
                    })
                    .create().show();
            // Menu item 2 Help
        } else if (item.getItemId() == R.id.homepage) {
            // Navigate to MainActivity
            Intent nextPage = new Intent(TrackListActivity.this, MainActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.favoriteSong) {
            // Navigate to FavoriteSongActivity
            Intent nextPage = new Intent(TrackListActivity.this, FavoriteSongActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            // Navigate to SearchArtistActivity
            Intent nextPage = new Intent(TrackListActivity.this, SearchArtistActivity.class);
            startActivity(nextPage);
        }
        else {
        }
        return super.onOptionsItemSelected(item);
    }
    // Method to send network request to fetch track data
    private void sendRequest(String arg) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = arg; // URL for fetching track data

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response from server
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray data = jsonResponse.getJSONArray("data");

                            // Iterate through the JSONArray to get individual track data
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject result = data.getJSONObject(i);
                                JSONObject albumObject = result.getJSONObject("album");
                                // Set track details
                                Track track = new Track();
                                track.setId(result.getString("id"));
                                track.setTitle(result.getString("title"));
                                track.setTitle_short(result.getString("title_short"));
                                track.setRank(result.getString("rank"));
                                track.setDuration(result.getString("duration"));
                                track.setPicture_medium(albumObject.getString("cover_medium"));
                                track.setPicture_big(albumObject.getString("cover_big"));
                                track.setAlbum(albumObject.getString("title"));
                                tracks.add(track);// Add track to list
                            }

                            adapter.notifyDataSetChanged(); // Notify adapter of data change
                        } catch (JSONException e) {
                            e.printStackTrace(); // Print stack trace for any JSON parsing errors
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}