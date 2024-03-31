package com.college.converter.recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class RecipeSearchActivity extends AppCompatActivity implements Recipe_ID_Adapter.OnItemClickListener{

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


//    private TextView title, instructions, likes;
//    private ImageView img;
//    private DatabaseReference mRootRef;
//    private FirebaseAuth mAuth;
//    private JSONArray ingredientsArr;
//
//    private RecyclerView myrv;
//    private boolean like = false;
//    public String api_key = "6c93a30ed6624a03be850e3d2c118b6b";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recipe);
//        Objects.requireNonNull(getSupportActionBar()).hide();
//
//        final Intent intent = getIntent();
//        final String recipeId = Objects.requireNonNull(intent.getExtras()).getString("id");
//        mAuth = FirebaseAuth.getInstance();
//        //    final String id = mAuth.getCurrentUser().getUid();
//        mRootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("Bookmarks").child(recipeId);
//        Button fab = findViewById(R.id.recipe_fab2);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                like = !like;
//                mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (like) {
//                            Map favorites = new HashMap();
//                            favorites.put("img", intent.getExtras().getString("img"));
//                            favorites.put("title", intent.getExtras().getString("title"));
//                            mRootRef.setValue(favorites);
//                            Toast.makeText(RecipeSearchActivity.this, "The recipe has been Bookmarked.", Toast.LENGTH_SHORT).show();
//                        } else {
//                            try {
//                                mRootRef.setValue(null);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        String e = databaseError.toString();
//                        Toast.makeText(RecipeSearchActivity.this, e, Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//        img = findViewById(R.id.recipe_image);
//        title = findViewById(R.id.recipe_title);
//        instructions = findViewById(R.id.recipe_instructions);
//        getRecipeData(recipeId);
//
//        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.i("mRootRef", String.valueOf(dataSnapshot));
//                if (dataSnapshot.getValue() != null) {
//                    like = true;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        myrv.setLayoutManager(new GridLayoutManager(this, 2));
//    }
//
//
//    private void getRecipeData(final String recipeId) {
//        String URL = " https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=" + api_key;
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                URL,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            try {
//                                Picasso.get().load((String) response.get("image")).into(img);
//                            } catch (Exception e) {
//                                img.setImageResource(R.drawable.nopicture);
//                            }
//                            title.setText((String) response.get("title"));
//
//                            if (response.get("instructions").equals("")) {
//                                throw new Exception("No Instructions");
//                            } else
//                                instructions.setText(Html.fromHtml((String) response.get("instructions")));
//                        } catch (Exception e) {
//                            String msg  = null;
//                            try {
//                                msg = "Unfortunately, the recipe you were looking for not found, " +
//                                        "to view the original recipe click on the link below:" +
//                                        "<a href=" + response.get("spoonacularSourceUrl") + ">"
//                                        + response.get("spoonacularSourceUrl") + "</a>";
//                            } catch (JSONException ex) {
//                                throw new RuntimeException(ex);
//                            }
//                            instructions.setMovementMethod(LinkMovementMethod.getInstance());
//                            instructions.setText(Html.fromHtml(msg));
//                        }
//                    }
//
//
//
//
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("the res is error:", error.toString());
//                    }
//                }
//        );
//        requestQueue.add(jsonObjectRequest);
//    }}
