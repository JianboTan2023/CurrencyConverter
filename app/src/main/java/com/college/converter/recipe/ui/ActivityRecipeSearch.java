package com.college.converter.recipe.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.college.converter.R;
import com.college.converter.recipe.adapter.RecipeAdapter;
import com.college.converter.recipe.adapter.Recipe_ID_Adapter;
import com.college.converter.recipe.data.Recipe;
import com.college.converter.recipe.data.RecipeID;
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


public class ActivityRecipeSearch extends AppCompatActivity implements Recipe_ID_Adapter.OnItemClickListener{

    ArrayList<Recipe> recipes = new ArrayList<>();
    ArrayList<RecipeID> RecipeIDs = new ArrayList<>();
    Recipe_Adapter adapter;
    RecipeIDDAO rDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_search);
        Toolbar toolbar = findViewById(R.id.recipeToolBar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        adapter = new RecipeAdapter(this, recipes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String artistName = prefs.getString("Recipe Name", "");
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
            Toast.makeText(this, "Please wait......", Toast.LENGTH_SHORT).show();
            // Set the click listener
            adapter.setOnItemClickListener(this);
        });
    }

    @Override
    public void onItemClick(int position) {
        Recipe recipe = recipes.get(position);
        // Handle item click here
        // For example, start a new activity
        Intent nextPage = new Intent(ActivityRecipeSearch.this, ActivityRecipeIDList.class);
        nextPage.putExtra("url", artist.getArtistTrackList());
        startActivity(nextPage);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        Log.d("R", menu.toString());
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Menu help
        if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder( ActivityRecipeSearch.this );
            builder.setMessage("Steps:\n\n" +
                            "1. Type the  name in the editText.\n" +
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
            Intent nextPage = new Intent(ActivityRecipeSearch.this, ActivityRecipeFavorite.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            Intent nextPage = new Intent(ActivityRecipeSearch.this, ActivityRecipeSearch.class);
            startActivity(nextPage);
        }
        else {
        }
        return super.onOptionsItemSelected(item);
    }
    private void sendRequest(String rName) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + rName
                +"&apiKey=6c93a30ed6624a03be850e3d2c118b6b";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray data = jsonResponse.getJSONArray("data");
                            recipes.clear();

                            // Iterate through the JSONArray to get recipeId and data
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject result = data.getJSONObject(i);
                               Recipe recipe = new Recipe();
                                recipe.setRecipeName(result.getString("name"));
                                recipe.setIngredient(result.getString("ingredient"));
                                recipe.setRecipePicture_medium(result.getString("picture"));

                                recipes.add(recipe);
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


