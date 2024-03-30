package com.college.converter.song.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import com.college.converter.FirstActivity;
import com.college.converter.MainActivity;
import com.college.converter.R;
import com.college.converter.SecondActivity;
import com.college.converter.song.adapter.Artist_Adapter;
import com.college.converter.song.data.Artist;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchArtistActivity extends AppCompatActivity implements Artist_Adapter.OnItemClickListener {
        ArrayList<Artist> artists = new ArrayList<>();
        Artist_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deezer);
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        adapter = new Artist_Adapter(this, artists);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 4. Set onClickListener for the search button.
        Button btn = findViewById(R.id.searchButton);
        btn.setOnClickListener(click -> {
            Log.d("Test", "Trigger btn");
            TextView nameInput = findViewById(R.id.nameInput);
            String name = nameInput.getText().toString();
            nameInput.setText("");
            sendRequest(name);
        });
        // Set the click listener
        adapter.setOnItemClickListener(this);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.forth_id);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int item_id = item.getItemId();
            if ( item_id == R.id.home_id ) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else if (item_id == R.id.first_id) {
                startActivity(new Intent(getApplicationContext(), FirstActivity.class));
                return true;
            }
            else if ( item_id == R.id.second_id ) {
                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                return true;
            }
            else if ( item_id == R.id.third_id ) {
            //    startActivity(new Intent(getApplicationContext(), Dictionary.class));
                return true;
            }
            else if ( item_id == R.id.forth_id ) {
                return true;
            }
            return false;
        });
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
                                Log.d("debug", artist.getArtistName());
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

    @Override
    public void onItemClick(int position) {
        Artist artist = artists.get(position);
        // Handle item click here
        // For example, start a new activity
        Intent nextPage = new Intent(SearchArtistActivity.this, TrackListActivity.class);
        nextPage.putExtra("url", artist.getArtistTrackList());
        startActivity(nextPage);

    }
}

