package com.college.converter.recipe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ActivityRecipeIDDetail extends AppCompatActivity {

    private boolean isLiked = false;
    RecipeIDDAO rDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeid_detail);

        Toolbar toolbar = findViewById(R.id.recipeToolBar);
        setSupportActionBar(toolbar);

        RecipeIDDatabase db = Room.databaseBuilder(getApplicationContext(), RecipeIDDatabase.class,
                "database-track").build();
        rDAO = db.rDAO();

        Intent intent = getIntent();
        String recipeId = intent.getStringExtra("recipeId");
        String picture = intent.getStringExtra("picture_medium");
        String title  = intent.getStringExtra("title");
        String ingredient = intent.getStringExtra("ingredient");
        String instruction     = intent.getStringExtra("instruction");

        RecipeID RecipeID = new RecipeID(recipeId, picture, title, ingredient, instruction);

        ImageView picture = findViewById(R.id.imageView2);
        TextView title = findViewById(R.id.recipe_title);
        TextView ingredient  = findViewById(R.id.ingredientdetail);
        TextView instruction = findViewById(R.id.instructiondetail);

        Picasso.get().load(picture).into(picture);
        recipeId.setText(recipeId);
        title.setText(title);
        picture.setText(picture);

        ImageButton favBtn = findViewById(R.id.favBtn);
        favBtn.setOnClickListener(clk -> {
            Executor thread = Executors.newSingleThreadExecutor();

            thread.execute(() ->
            {
                try {
                    rDAO.insertRecipeID(RecipeID);
                    runOnUiThread(() -> {
                        Toast.makeText(this, R.string.favconfirmation, Toast.LENGTH_SHORT).show();
                    });
                }catch (Exception e){

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
