package com.college.converter.recipe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.college.converter.R;
import com.college.converter.recipe.adapter.RecipeAdapter;
import com.college.converter.recipe.adapter.Recipe_ID_Adapter;
import com.college.converter.recipe.data.RecipeID;

import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityRecipeIDList extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    Recipe_ID_Adapter adapter;

    ArrayList<RecipeID> RecipeIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeid_list);

        Toolbar toolbar = findViewById(R.id.recipeToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Recipe");

        RecyclerView recyclerView = findViewById(R.id.recipeIdRecyclerView);
        adapter = new Recipe_ID_Adapter(this, RecipeIDs);
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
        RecipeID RecipeID = RecipeIDs.get(position);
        // Handle item click here
        // For example, start a new activity
        Intent nextPage = new Intent(ActivityRecipeIDList.this, ActivityRecipeIDDetail.class);
        nextPage.putExtra("recipeId", RecipeID.getRecipeId());
        nextPage.putExtra("cover_m", RecipeID.getPicture_medium());
        nextPage.putExtra("title", RecipeID.getTitle());
        nextPage.putExtra("ingredient", RecipeID.getIngredient());
        nextPage.putExtra("instruction", RecipeID.getInstruction());
        startActivity(nextPage);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Menu help
        if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder( ActivityRecipeIDList.this );
            builder.setMessage(R.string.recipe_instruction)
                    .setTitle(R.string.favconfirmation)
                    .setPositiveButton("Ok", (dialog, which) -> {
                    })
                    .create().show();
            // Menu item 2 Help
        } else if (item.getItemId() == R.id.homepage) {
            Intent nextPage = new Intent(ActivityRecipeIDList.this, ActivityRecipeFavorite.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            Intent nextPage = new Intent(ActivityRecipeIDList.this, ActivityRecipeSearch.class);
            startActivity(nextPage);
        }
        else {
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendRequest(String ID) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.spoonacular.com/recipes/" + ID +"/information?apiKey=6c93a30ed6624a03be850e3d2c118b6b";


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
                                JSONObject recipeIdObject = result.getJSONObject("recipeId");
                                RecipeID RecipeID = new RecipeID();
                                RecipeID.setTitle(result.getString("title"));
                                RecipeID.setTitle(result.getString("title_short"));
                                RecipeID.setPicture_medium(recipeIdObject.getString("picture"));
                                RecipeID.setIngredient(recipeIdObject.getString("ingredient"));
                                RecipeID.setInstruction(RecipeIDObject.getString("instruction"));
                                com.college.converter.recipe.data.RecipeID.add(track);
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
