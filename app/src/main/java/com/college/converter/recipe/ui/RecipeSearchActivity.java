package com.college.converter.recipe.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.college.converter.MainActivity;
import com.college.converter.R;
import com.college.converter.dictionary.DictionaryActivity;
import com.college.converter.recipe.adapter.RecipeAdapter;
import com.college.converter.recipe.data.Recipe;
import com.college.converter.recipe.data.RecipeIDDAO;
import com.college.converter.sunlookup.SunActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RecipeSearchActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener{

    ArrayList<Recipe> recipes = new ArrayList<>();
    ArrayList<RecipeID> recipeIDs = new ArrayList<>();
    RecipeAdapter adapter;
    RecipeIDDAO rDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_search);
        Toolbar toolbar = findViewById(R.id.recipeToolBar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        adapter = new RecipeAdapter(this, recipes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences prefs = getSharedPreferences("results", Context.MODE_PRIVATE);
        String recipeSearch = prefs.getString("My data", "");
        EditText nameEditText = findViewById(R.id.recipeSearchText);
        nameEditText.setText(recipeSearch);

//add menu bar at the top of the page


        // this setion set up click function and bottom navigation
        Button btn = findViewById(R.id.searchButton);
        btn.setOnClickListener(click -> {
            TextView nameInput = findViewById(R.id.recipeSearchText);
            String name = nameInput.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("recipeName", nameEditText.getText().toString());
            editor.apply();
            nameInput.setText("");
            sendRequest(name);
            Toast.makeText(this, R.string.waitrequest, Toast.LENGTH_SHORT).show();
            // Set the click listener
            //adapter.setOnItemClickListener((RecipeAdapter.OnItemClickListener) this);
            adapter.setOnItemClickListener(this);
        });

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
                startActivity(new Intent(getApplicationContext(), RecipeSearchActivity.class));
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

//onItemClick function for item clicked from RecyclerView
   @Override
   public void onItemClick(int position) {
        Recipe recipe = recipes.get(position);

        Intent nextPage = new Intent(RecipeSearchActivity.this, ActivityRecipeIDList.class);
        nextPage.putExtra("url", recipe.getId());
        startActivity(nextPage);
    }


    /**
     * this part is to send API request to search recipe from other website
     * @param recipeName is the user edit content
     */
    private void sendRequest(String recipeName) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + recipeName
                +"&apiKey=6c93a30ed6624a03be850e3d2c118b6b";


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray results = jsonResponse.getJSONArray("results");
                            recipes.clear();

                            // Iterate through the JSONArray to get recipeId and data

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject result = results.getJSONObject(i);
                               Recipe recipe = new Recipe();
                                recipe.setId(result.getString("id"));
                                recipe.setTitle(result.getString("title"));
                                recipe.setImage(result.getString("image"));

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


