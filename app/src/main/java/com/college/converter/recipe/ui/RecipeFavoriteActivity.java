package com.college.converter.recipe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.college.converter.MainActivity;
import com.college.converter.R;
import com.college.converter.dictionary.DictionaryActivity;
import com.college.converter.recipe.adapter.RecipeFavoriteAdapter;
import com.college.converter.recipe.data.Recipe;
import com.college.converter.recipe.data.RecipeDAO;
import com.college.converter.recipe.data.RecipeDatabase;
import com.college.converter.song.ui.SearchArtistActivity;
import com.college.converter.sunlookup.SunActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author Kelly Wu
 * @section Lab 021
 * this class is to add favorite list and update recipe database
 *
 */
public class RecipeFavoriteActivity extends AppCompatActivity {

    private RecyclerView recipeRecyclerView;
    List<Recipe> recipes = new ArrayList<>();

    private Toolbar toolbar;

    private RecipeDAO recipeDAO;

    private RecipeFavoriteAdapter recipeAdapter;

    /**
     * Initializes the activity, setting up the toolbar, RecyclerView, and
     * BottomNavigationView. It also initializes the database connection and
     * loads the list of favorite recipes.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle
     *                           contains the data it most recently supplied in
     *                           onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe_list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.favorite_recipes);

        recipeRecyclerView = findViewById(R.id.recyclerview_recipe_list);

        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //database
        recipeDAO = RecipeDatabase.getDbInstance(this).recipeDAO();

        recipeAdapter = new RecipeFavoriteAdapter(this, recipes);

        onRefreshList();

        setupBottomNavigationView(); 
    }
    
    /**
     * Refreshes the list of favorite list by retrieving the latest data
     * from the database and updating the adapter in RecyclerView.
     */
    private void onRefreshList() {
        recipes.clear();
        Executors.newSingleThreadExecutor().execute(() -> {
            if (recipeDAO.getAllRecipes() != null) {
                recipes.addAll( recipeDAO.getAllRecipes() );
            }
            runOnUiThread( () ->  recipeRecyclerView.setAdapter( recipeAdapter ));
        });
    }

    /**
     * Called when the activity has been stopped, before it is started again.
     * It triggers a refresh of the recipe list to ensure the UI is up to date
     * with the latest changes.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        onRefreshList();
    }


      /**
     * Sets up the bottom navigation view and item selection handling
     */
    protected void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.second_id);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.home_id) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else if (itemId == R.id.first_id) {
                startActivity(new Intent(getApplicationContext(), SunActivity.class));
            } else if (itemId == R.id.second_id) {
                startActivity(new Intent(getApplicationContext(), RecipeSearchActivity.class));
            } else if (itemId == R.id.third_id) {
                startActivity(new Intent(getApplicationContext(), DictionaryActivity.class));
            } else if (itemId == R.id.forth_id) {
                startActivity(new Intent(getApplicationContext(), SearchArtistActivity.class));
            }
            return true; // Return true to display the item as the selected item
        });
    }

    /**
     * Initializes the contents of the options menu.
     *
     * @param menu The options menu in which items are placed.
     * @return true for the menu to be displayed; false it will not be shown.
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
                    new androidx.appcompat.app.AlertDialog.Builder(RecipeFavoriteActivity.this);
            builder1.setMessage(getString(R.string.recipe_instruction));
            builder1.setTitle(getString(R.string.recipe_search_info_title));

            builder1.create().show();
        }
        else if (id ==  R.id.homepage) {
            Toast.makeText(this, getString(R.string.back), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), RecipeSearchActivity.class));
        }
        return super.onOptionsItemSelected(item);

    }
}

