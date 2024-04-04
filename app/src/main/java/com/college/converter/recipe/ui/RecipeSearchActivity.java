package com.college.converter.recipe.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.college.converter.MainActivity;
import com.college.converter.R;
import com.college.converter.dictionary.DictionaryActivity;
import com.college.converter.song.ui.SearchArtistActivity;
import com.college.converter.sunlookup.SunActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Kelly Wu
 * @section Lab 021
 * this class is to get recipe lists by request
 * using API or database, and get recipe Id
 *
 */

public class RecipeSearchActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "RecipePreference";
    private static final String SEARCH_TERM_KEY = "RecipeSearchKey";

    private EditText searchTermEdit;
    private Button searchBtn;
    private RecyclerView recipeRecyclerView;
    private List<Recipe> recipeList = new ArrayList<>();
    private RecipeAdapter recipeAdapter;
    private RequestQueue queue;
    private Toolbar toolbar;
    //protected BottomNavigationView bottomNavigationView;
    protected BottomNavigationView bottomNavigationView;
    private Button favoriteBtn;

    /**
     *
     * @param savedInstanceState this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.receip_title);

//set up search result display in recyclerview
        searchTermEdit = findViewById(R.id.searchEditText);
        searchBtn = findViewById(R.id.search_button);
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);

//sharedPreferences used to save last searched term
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        searchTermEdit.setText(prefs.getString(SEARCH_TERM_KEY, ""));

//set up RecyclerView
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this, recipeList);
        recipeRecyclerView.setAdapter(recipeAdapter);

//volley library method to create new request queue and associate with the specified context
        queue = Volley.newRequestQueue(this);
        //favorite button for saved recipes
        favoriteBtn = findViewById(R.id.favorite_button);

        //search button set up action
        searchBtn.setOnClickListener(view -> {
            String query = searchTermEdit.getText().toString().trim();
            if (!query.isEmpty()) {
                searchRecipe(query);
                prefs.edit().putString(SEARCH_TERM_KEY, query).apply();
            } else {
                Toast.makeText(RecipeSearchActivity.this, R.string.recipe_title, Toast.LENGTH_SHORT).show();
            }
        });

        //favorite button set up action
        favoriteBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RecipeFavoriteActivity.class));
        });

        // set up BottomNavigationView
        setupBottomNavigationView();
    }

    /**
     * this part is recipe search using the specified query string, sends a request to the external API.
     * @param query search term to query the recipeId.
     */
    private void searchRecipe(String query) {
        String apiKey = "6c93a30ed6624a03be850e3d2c118b6b";
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + query + "&apiKey=" + apiKey;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        recipeList.clear();

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject recipeObject = jsonArray.getJSONObject(i);
                            String title = recipeObject.getString("title");
                            String imageUrl = recipeObject.getString("image");
                            int id = recipeObject.getInt("id");

                            Recipe recipe = new Recipe(id, title, imageUrl);
                            recipe.setId(id);
                            recipeList.add(recipe);
                        }
                        recipeAdapter.notifyDataSetChanged();
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(RecipeSearchActivity.this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(RecipeSearchActivity.this, getString(R.string.fail), Toast.LENGTH_SHORT).show());

        queue.add(stringRequest);
    }

    /**
     * set up bottom navigation view
     * allows jump to different sections of the application.
     */
    protected void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.second_id);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home_id) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else if (itemId == R.id.first_id) {
                startActivity(new Intent(getApplicationContext(), SunActivity.class));
            } else if (itemId == R.id.second_id) {
                //  startActivity(new Intent(getApplicationContext(), RecipeSearchActivity.class));
            } else if (itemId == R.id.third_id) {
                startActivity(new Intent(getApplicationContext(), DictionaryActivity.class));
            } else if (itemId == R.id.forth_id) {
                startActivity(new Intent(getApplicationContext(), SearchArtistActivity.class));
            }
            return true; // Return true to display the item as the selected item
        });
    }

    /**
     * menu options for the activity from a menu resource.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     * item has two items, home and help
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if ( id ==  R.id.recipe_help)
        {
            androidx.appcompat.app.AlertDialog.Builder builder1 =
                    new androidx.appcompat.app.AlertDialog.Builder(RecipeSearchActivity.this);
            builder1.setMessage(getString(R.string.recipe_instruction));
            builder1.setTitle(getString(R.string.recipe_search_info_title));

            builder1.create().show();
        }
        else if (id ==  R.id.homepage) {
            Toast.makeText(this, getString(R.string.back), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);

    }


}