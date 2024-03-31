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
import com.college.converter.R;
import com.college.converter.song.adapter.Track_Adapter;
import com.college.converter.song.data.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrackListActivity extends AppCompatActivity implements Track_Adapter.OnItemClickListener {

    Track_Adapter adapter;
    ArrayList<Track> tracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer_tracklist);

        Toolbar toolbar = findViewById(R.id.deezerToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Track List");

        RecyclerView recyclerView = findViewById(R.id.trackRecyclerView);
        adapter = new Track_Adapter(this, tracks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        sendRequest(url);

        // Set the click listener
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Track track = tracks.get(position);
        // Handle item click here
        // For example, start a new activity
        Intent nextPage = new Intent(TrackListActivity.this, TrackDetailsActivity.class);
        nextPage.putExtra("album", track.getAlbum());
        nextPage.putExtra("cover_l", track.getPicture_big());
        nextPage.putExtra("cover_m", track.getPicture_medium());
        nextPage.putExtra("song", track.getTitle_short());
        nextPage.putExtra("duration", track.getDuration());
        nextPage.putExtra("id", track.getId());
        nextPage.putExtra("rank", track.getRank());
        startActivity(nextPage);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deezer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Menu help
        if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder( TrackListActivity.this );
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
            Intent nextPage = new Intent(TrackListActivity.this, FavoriteSongActivity.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            Intent nextPage = new Intent(TrackListActivity.this, SearchArtistActivity.class);
            startActivity(nextPage);
        }
        else {
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendRequest(String arg) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = arg;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray data = jsonResponse.getJSONArray("data");

                            // Iterate through the JSONArray to get individual track data
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject result = data.getJSONObject(i);
                                JSONObject albumObject = result.getJSONObject("album");
                                Track track = new Track();
                                track.setId(result.getString("id"));
                                track.setTitle(result.getString("title"));
                                track.setTitle_short(result.getString("title_short"));
                                track.setRank(result.getString("rank"));
                                track.setDuration(result.getString("duration"));
                                track.setPicture_medium(albumObject.getString("cover_medium"));
                                track.setPicture_big(albumObject.getString("cover_big"));
                                track.setAlbum(albumObject.getString("title"));
                                tracks.add(track);
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