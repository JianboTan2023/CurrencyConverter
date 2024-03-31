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

import com.college.converter.song.adapter.Artist_Adapter;
import com.college.converter.song.data.Artist;
import com.college.converter.song.data.Track;
import com.college.converter.song.data.TrackDAO;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchArtistActivity extends AppCompatActivity implements Artist_Adapter.OnItemClickListener{

    ArrayList<Artist> artists = new ArrayList<>();
    ArrayList<Track>  tracks  = new ArrayList<>();
    Artist_Adapter adapter;
    TrackDAO trackDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deezer_search);
        Toolbar toolbar = findViewById(R.id.deezerToolBar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        adapter = new Artist_Adapter(this, artists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String artistName = prefs.getString("artistName", "");
        EditText nameEditText = findViewById(R.id.nameInput);
        nameEditText.setText(artistName);
        // 4. Set onClickListener for the search button.
        Button btn = findViewById(R.id.searchButton);
        btn.setOnClickListener(click -> {
            TextView nameInput = findViewById(R.id.nameInput);
            String name = nameInput.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("artistName", nameEditText.getText().toString());
            editor.apply();
            nameInput.setText("");
            sendRequest(name);
            Toast.makeText(this, "Acquiring Result......", Toast.LENGTH_SHORT).show();
            // Set the click listener
            adapter.setOnItemClickListener(this);
        });
    }

    @Override
    public void onItemClick(int position) {
        Artist artist = artists.get(position);
        // Handle item click here
        // For example, start a new activity
        Intent nextPage = new Intent(SearchArtistActivity.this, TrackListActivity.class);
        nextPage.putExtra("url", artist.getArtistTrackList());
        startActivity(nextPage);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deezer_menu, menu);
        Log.d("D", menu.toString());
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Menu help
        if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder( SearchArtistActivity.this );
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
            Intent nextPage = new Intent(SearchArtistActivity.this, FavoriteSongActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            Intent nextPage = new Intent(SearchArtistActivity.this, SearchArtistActivity.class);
            startActivity(nextPage);
        }
        else {
        }
        return super.onOptionsItemSelected(item);
    }
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
                            artists.clear();

                            // Iterate through the JSONArray to get individual artist data
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject result = data.getJSONObject(i);
                                Artist artist = new Artist();
                                artist.setArtistName(result.getString("name"));
                                artist.setArtistNb_album(result.getString("nb_album"));
                                artist.setArtistNb_fan(result.getString("nb_fan"));
                                artist.setArtistPicture_medium(result.getString("picture_medium"));
                                artist.setArtistTrackList(result.getString("tracklist"));
                                artists.add(artist);
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
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
