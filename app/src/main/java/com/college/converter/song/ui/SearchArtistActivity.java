package com.college.converter.song.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;


import com.college.converter.R;


import com.college.converter.MainActivity;

import com.college.converter.dictionary.DictionaryActivity;

import com.college.converter.recipe.ui.ActivityRecipeSearch;
import com.college.converter.song.adapter.Artist_Adapter;
import com.college.converter.song.data.Artist;
import com.college.converter.song.data.Track;
import com.college.converter.song.data.TrackDAO;

import com.college.converter.sunlookup.SunActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
// Activity class to search for artists
public class SearchArtistActivity extends AppCompatActivity implements Artist_Adapter.OnItemClickListener{

    ArrayList<Artist> artists = new ArrayList<>();// List to store artists
    ArrayList<Track>  tracks  = new ArrayList<>();
    Artist_Adapter adapter;// Adapter for RecyclerView
    TrackDAO trackDAO;// Data Access Object for managing track data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deezer_search);// Set activity layout
        Toolbar toolbar = findViewById(R.id.deezerToolBar);// Toolbar initialization
        setSupportActionBar(toolbar);// Set toolbar as action bar

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);// Initialize RecyclerView
        adapter = new Artist_Adapter(this, artists);// Initialize adapter
        recyclerView.setAdapter(adapter);// Set adapter to RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));// Set layout manager
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);// Initialize SharedPreferences
        String artistName = prefs.getString("artistName", "");// Retrieve artist name from SharedPreferences
        EditText nameEditText = findViewById(R.id.nameInput);// Find EditText for artist name input
        nameEditText.setText(artistName);// Set artist name in EditText

        Button btn = findViewById(R.id.searchButton);// Find search button
        // Set onClickListener for the search button
        btn.setOnClickListener(click -> {
            TextView nameInput = findViewById(R.id.nameInput);// Find TextView for artist name input
            // Get artist name from input
            String name = nameInput.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();// Get SharedPreferences editor
            editor.putString("artistName", nameEditText.getText().toString());// Store artist name in SharedPreferences
            editor.apply();// Apply changes
            nameInput.setText("");// Clear artist name input field
            sendRequest(name);// Send API request to search for artist

            Toast.makeText(this, R.string.ds_acquiring_result, Toast.LENGTH_SHORT).show();// Show toast message
            adapter.setOnItemClickListener(this);// Set click listener for RecyclerView items
        });

        // Bottom navigation view initialization
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.forth_id);

        // Set item selected listener for bottom navigation view
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int item_id = item.getItemId();
            // Navigate to respective activities based on selected item
            if ( item_id == R.id.home_id ) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else if (item_id == R.id.first_id) {
                startActivity(new Intent(getApplicationContext(), SunActivity.class));
                return true;
            }
            else if ( item_id == R.id.second_id ) {
                startActivity(new Intent(getApplicationContext(), ActivityRecipeSearch.class));
                return true;
            }
            else if ( item_id == R.id.third_id ) {
                startActivity(new Intent(getApplicationContext(), DictionaryActivity.class));
                return true;
            }
            else if ( item_id == R.id.forth_id ) {

                return true;
            }
            return false;
        });

    }
    // Method to handle item click in RecyclerView
    @Override
    public void onItemClick(int position) {
        Artist artist = artists.get(position);// Get clicked artist
        // Start TrackListActivity and pass URL for artist's track list
        Intent nextPage = new Intent(SearchArtistActivity.this, TrackListActivity.class);
        nextPage.putExtra("url", artist.getArtistTrackList());
        startActivity(nextPage);
    }
    // Method to create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deezer_menu, menu);// Inflate menu layout
        Log.d("D", menu.toString());// Log menu information
        return super.onCreateOptionsMenu(menu);// Return menu creation status
    }

    // Method to handle options menu item selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle menu item selection
        if (item.getItemId() == R.id.help) {
            // Show help dialog
            AlertDialog.Builder builder = new AlertDialog.Builder( SearchArtistActivity.this );
            builder.setMessage(R.string.deezer_help_info)
                    .setTitle(R.string.deezer_how_to_use_the_interface)
                    .setPositiveButton("Ok", (dialog, which) -> {
                    })
                    .create().show();
            // Menu item 2 Help
        } else if (item.getItemId() == R.id.homepage) {
            // Navigate to MainActivity
            Intent nextPage = new Intent(SearchArtistActivity.this, MainActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.favoriteSong) {
            // Navigate to FavoriteSongActivity
            Intent nextPage = new Intent(SearchArtistActivity.this, FavoriteSongActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            // Navigate to SearchArtistActivity
            Intent nextPage = new Intent(SearchArtistActivity.this, SearchArtistActivity.class);
            startActivity(nextPage);
        }
        else {
        }
        return super.onOptionsItemSelected(item);// Return menu item selection status
    }

    // Method to send API request to search for artists
    private void sendRequest(String name) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.deezer.com/search/artist/?q=" + name;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray data = jsonResponse.getJSONArray("data");
                            artists.clear();// Clear artists list

                            // Iterate through the JSONArray to get individual artist data
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject result = data.getJSONObject(i);
                                Artist artist = new Artist();// Create new Artist object
                                artist.setArtistName(result.getString("name"));
                                artist.setArtistNb_album(result.getString("nb_album"));
                                artist.setArtistNb_fan(result.getString("nb_fan"));
                                artist.setArtistPicture_medium(result.getString("picture_medium"));
                                artist.setArtistTrackList(result.getString("tracklist"));
                                artists.add(artist);// Add artist to list
                            }

                            adapter.notifyDataSetChanged();// Notify adapter of data change
                        } catch (JSONException e) {
                            e.printStackTrace();// Log JSON parsing error
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
