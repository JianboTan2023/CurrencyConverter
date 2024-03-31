package com.college.converter.recipe.ui;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.college.converter.R;
import com.college.converter.recipe.data.RecipeID;
import com.college.converter.recipe.data.RecipeIDDAO;
import com.college.converter.recipe.data.RecipeIDDatabase;

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
                        Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
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
