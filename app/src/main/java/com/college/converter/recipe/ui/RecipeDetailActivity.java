package com.college.converter.recipe.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.college.converter.R;
import com.college.converter.databinding.ActivityRecipeDetailBinding;
import com.college.converter.recipe.data.Recipe;
import com.college.converter.recipe.data.RecipeDAO;
import com.college.converter.recipe.data.RecipeDatabase;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

/**
 * @author Kelly Wu
 * @section Lab 021
 * this class is to get recipe details by second request from API or database,
 */
public class RecipeDetailActivity extends AppCompatActivity {

    ActivityRecipeDetailBinding binding;
    private RequestQueue queue;

    // Constant keys for identifying the source of the data
    public static final String SOURCE_LIST = "source_list";
    public static final String SOURCE_FAVORITE = "source_favorite";

    /**
     * Initializes the activity, sets up the view binding,
     * display the recipe information.
     * displays the stored data.
     *display data from an external API if not existed in database
     */
    @Override
    /**
     * savedInstanceState Bundle object containing data from previously saved state
     * this is the first part for retriving data upon search request
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String source = getIntent().getStringExtra("source");
        int recipeId = getIntent().getIntExtra("recipeId", -1);
        if (SOURCE_FAVORITE.equals(source)) {
            Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");
            assert recipe != null;
            binding.progressBar.setVisibility(View.GONE);
            binding.recipeTitle.setText(recipe.getTitle());
            binding.recipeSummary.setText(recipe.getSummary());
            binding.recipeSourceUrl.setText(recipe.getSpoonacularSourceUrl());
            Picasso.get().load(recipe.getImageUrl()).into(binding.recipeImage);
            binding.addFavroite.setText(R.string.remove_favorite);
            binding.addFavroite.setOnClickListener(clk -> removeFavoirte(recipe));
            return;
        }
        queue = Volley.newRequestQueue(this);
        binding.progressBar.setVisibility(View.VISIBLE);
        searchRecipe(recipeId);
    }

    /**
     * this part is to removes a recipe from the favorites list using a background executor.
     * Snackbar message shows removal completion.
     */
    private void removeFavoirte(Recipe recipe) {
        Executors.newSingleThreadExecutor().execute(() -> {
            RecipeDAO recipeDAO = RecipeDatabase.getDbInstance(RecipeDetailActivity.this).recipeDAO();
            recipeDAO.deleteRecipe(recipe);
        });
        Snackbar.make(binding.getRoot(), getString(R.string.deleteConfirm), Snackbar.LENGTH_SHORT).show();
    }

    /**
     *  @param recipe The recipe object to be added to favorites.
     * add a recipe data into the favorites list.
     * presents a confirmation message before adding.
     * calls background executor to insert the recipe into the database
     *
     */
    private void addFavorite(Recipe recipe) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to add '" + recipe.getTitle() + "' to your Favorite List?")
                .setTitle("Question")
                .setNegativeButton("no", (dialog, cl) -> {})
                .setPositiveButton("yes", (dialog, cl) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        RecipeDAO recipeDAO = RecipeDatabase.getDbInstance(RecipeDetailActivity.this).recipeDAO();
                        recipeDAO.insertRecipe(recipe);
                    });
                    Snackbar.make(binding.getRoot(), getString(R.string.addedMessage), Snackbar.LENGTH_SHORT).show();
                    binding.addFavroite.setText(R.string.favoriteadded);
                }).create().show();
    }

    /**
     * using GET request to retrieve the data based on its ID .
     * second request, using API to get data from external link
     * @param query The unique ID of the recipe to fetch details for.
     */
    private void searchRecipe(int query) {
        if (query == -1) {
            Snackbar.make(binding.getRoot(), getString(R.string.receip_not_found), Snackbar.LENGTH_SHORT).show();
            return;
        }
        String apiKey = "6c93a30ed6624a03be850e3d2c118b6b";
        String url = "https://api.spoonacular.com/recipes/" + query + "/information?includeNutrition=false&apiKey=" + apiKey;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        Recipe recipe = getRecipe(response);
                        binding.recipeTitle.setText(recipe.getTitle());
                        binding.recipeSummary.setText(recipe.getSummary());
                        binding.recipeSourceUrl.setText(recipe.getSpoonacularSourceUrl());
                        Picasso.get().load(recipe.getImageUrl()).into(binding.recipeImage);
                        binding.progressBar.setVisibility(View.GONE);
                        binding.addFavroite.setOnClickListener(clk -> addFavorite(recipe));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(RecipeDetailActivity.this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(RecipeDetailActivity.this, getString(R.string.tryagain), Toast.LENGTH_SHORT).show());

        queue.add(stringRequest);
    }

    /**
     * Parses the JSON response from the API and returns a Recipe object.
     *
     * @param response The string representation of the JSON response.
     * @return A Recipe object with data extracted from the JSON response.
     * @throws JSONException If parsing the JSON data fails.
     */
    @androidx.annotation.NonNull
    private static Recipe getRecipe(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        Recipe recipe = new Recipe();
        recipe.setId(jsonObject.getInt("id"));
        recipe.setTitle(jsonObject.getString("title"));
        recipe.setImageUrl(jsonObject.getString("image"));
        recipe.setSummary(jsonObject.getString("summary"));
        recipe.setSourceUrl(jsonObject.getString("sourceUrl"));
        recipe.setSpoonacularSourceUrl(jsonObject.getString("spoonacularSourceUrl"));
        return recipe;
    }

    /**
     * Initializes the contents of the Activity's standard options menu.
     * This is only called once, the first time the options menu is displayed.
     *
     * @param menu The options menu in which items are placed.
     * @return true for the menu to be displayed; false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        return true;
    }

    /**
     * Handles action bar item clicks. The action bar will automatically handle clicks on the
     * Home/Up button, so long as a parent activity in AndroidManifest.xml is specified.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed,
     *         true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.help) {
            // Show help information
            Toast.makeText(this, getString(R.string.instruction), Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.home) {
            // Navigate to the home activity
            startActivity(new Intent(getApplicationContext(), RecipeSearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
