package com.college.converter.recipe.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.college.converter.R;
import com.college.converter.recipe.data.RecipeID;
import com.college.converter.recipe.data.RecipeIDDAO;
import com.college.converter.recipe.data.RecipeIDDatabase;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Kelly Wu
 * @section Lab 021
 * this class is to get recipe details by second request
 */
public class ActivityRecipeIDDetail extends AppCompatActivity {

    private boolean isLiked = false;
    RecipeIDDAO rDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.recipeToolBar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_recipeid_detail);

        RecipeIDDatabase db = Room.databaseBuilder(getApplicationContext(), RecipeIDDatabase.class,
                "database-track").build();
        rDAO = db.RecipeIDDAO();

        Intent intent = getIntent();
        String recipeId = intent.getStringExtra("id");
        String rImage = intent.getStringExtra("image");
        String recipeTitle = intent.getStringExtra("title");
        String recipeSummary = intent.getStringExtra("summary");
        String recipeSourceUrl = intent.getStringExtra("sourceUrl");

//use received data to set up track object
        RecipeID recipeID = new RecipeID(recipeId, rImage,
                recipeTitle, recipeSourceUrl, recipeSummary);

        ImageView image = findViewById(R.id.recipeImage);
        TextView title = findViewById(R.id.recipe_title);
        TextView sourceUrl = findViewById(R.id.source);
        TextView summary = findViewById(R.id.summary);

        Picasso.get().load(rImage).into(image);
        title.setText(recipeTitle);
        summary.setText(recipeSummary);
        sourceUrl.setText(recipeSourceUrl);
        ImageButton favBtn = findViewById(R.id.favBtn);
        boolean result = true;
        favBtn.setOnClickListener(clk -> {
            Executor thread = Executors.newSingleThreadExecutor();

            thread.execute(() ->
            {
                try {
                    rDAO.insertRecipeID(recipeID);
                    runOnUiThread(() -> {
                        Toast.makeText(this, R.string.favconfirmation, Toast.LENGTH_SHORT).show();
                    });
                } catch (Exception e) {

                }
            });

        });


        ImageButton homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(clk -> {
            Intent nextPage = new Intent(ActivityRecipeIDDetail.this, ActivityRecipeFavorite.class);
            startActivity(nextPage);
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        Log.d("R", menu.toString());
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Menu help
        if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder( ActivityRecipeIDDetail.this );
            builder.setMessage(R.string.recipe_instruction)
                    .setTitle(R.string.how_to_use_interface)
                    .setPositiveButton("Ok", (dialog, which) -> {
                    })
                    .create().show();
            // Menu item 2 Help
        } else if (item.getItemId() == R.id.homepage) {
            Intent nextPage = new Intent(ActivityRecipeIDDetail.this, ActivityRecipeFavorite.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            Intent nextPage = new Intent(ActivityRecipeIDDetail.this, ActivityRecipeSearch.class);
            startActivity(nextPage);
        }
        else {
        }
        return super.onOptionsItemSelected(item);
    }
}
