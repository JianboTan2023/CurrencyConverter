package

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.college.converter.MainActivity;
import com.college.converter.R;
import com.college.converter.SecondActivity;
import com.college.converter.dictionary.DictionaryActivity;
import com.college.converter.recipe.adapter.RecipeFavoriteAdapter;
import com.college.converter.recipe.data.Recipe;
import com.college.converter.recipe.data.RecipeDAO;
import com.college.converter.recipe.data.RecipeDatabase;
import com.college.converter.sunlookup.SunActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recipesRecyclerView;
    List<Recipe> recipes = new ArrayList<>();

    private Toolbar toolbar;

    private RecipeDAO recipeDao;

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
        setContentView(R.layout.activity_favorite_food);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.favorite_recipes);

        recipesRecyclerView = findViewById(R.id.recycle_food);

        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //database
        recipeDao = RecipeDatabase.getDbInstance(this).recipeDao();

        recipeAdapter = new RecipeFavoriteAdapter(this, recipes);

        refreshList();
        // Now setup the BottomNavigationView
        setupBottomNavigationView(); // Add this line
    }

    /**
     * Sets up the bottom navigation view and item selection handling, providing
     * navigation to other activities within the application.
     */
    protected void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.second_id);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int item_id = item.getItemId();
            if ( item_id == R.id.home_id ) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else if (item_id == R.id.first_id) {
                startActivity(new Intent(getApplicationContext(), SunActivity.class));
                return true;
            }
            else if ( item_id == R.id.second_id ) {
                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
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

    /**
     * Refreshes the list of favorite recipes by retrieving the latest data
     * from the database and updating the adapter for the RecyclerView.
     */
    private void refreshList() {
        recipes.clear();
        Executors.newSingleThreadExecutor().execute(() -> {
            if (recipeDao.getAllRecipes() != null) {
                recipes.addAll( recipeDao.getAllRecipes() ); //Once you get the data from database
            }
            runOnUiThread( () ->  recipesRecyclerView.setAdapter( recipeAdapter )); //You can then load the RecyclerView
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
        refreshList();
    }
}
}
