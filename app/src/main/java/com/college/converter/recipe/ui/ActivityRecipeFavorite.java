package com.college.converter.recipe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.college.converter.R;
import com.college.converter.recipe.data.RecipeID;
import com.college.converter.recipe.data.RecipeIDDAO;
import com.college.converter.recipe.data.RecipeIDDatabase;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executors;

public class ActivityRecipeFavorite extends AppCompatActivity {
    FavoriteSongRecyclerViewAdapter adapter;
    ArrayList<RecipeID> RecipeIds = new ArrayList<>();
    RecipIDDAO rDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe);

        Toolbar toolbar = findViewById(R.id.recipeToolBar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.trackRecyclerView);

        RecipeIDDatabase db = Room.databaseBuilder(getApplicationContext(), RecipeIDDatabase.class,
                "database-recipeId").build();
        rDAO = db.rDAO();
        if (RecipeIds.size() == 0) {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                RecipeIds.addAll(rDAO.getAllRecipeIDs()); //Once you get the data from database
                runOnUiThread(() -> {
                    adapter = new FavoriteSongRecyclerViewAdapter(this, RecipeIds);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                });
            });
        }
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityRecipeFavorite.this);
            builder.setMessage(getString(R.string.help))
                    .setTitle(R.string.recipe_instruction)
                    .setPositiveButton("Ok", (dialog, which) -> {
                    })
                    .create().show();
            // Menu item 2 Help
        } else if (item.getItemId() == R.id.homepage) {
            Intent nextPage = new Intent(ActivityRecipeFavorite.this, ActivityRecipeFavorite.class);
            startActivity(nextPage);
        } else if (item.getItemId() == R.id.search) {
            Intent nextPage = new Intent(ActivityRecipeFavorite.this, ActivityRecipeSearch.class);
            startActivity(nextPage);
        } else {
        }
        return super.onOptionsItemSelected(item);
    }

    class RecipeFavoriteRecyclerViewAdapter extends RecyclerView
            .Adapter<RecipeFavoriteRecyclerViewAdapter.FavoriteSongViewRowHolder.RecipeFavoriteViewRowHolder> {

        private Context context;
        private ArrayList<RecipeID> RecipeIDs;
        RecipeIDDAO rDAO;

        public RecipeFavoriteRecyclerViewAdapter(Context context, ArrayList<RecipeID> recipeIds) {
            this.context = context;
            this.recipeIds = recipeIds;
        }

        @NonNull
        @Override
        public FavoriteSongViewRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recyclerview_favorite_recipe, parent, false);

            return new FavoriteSongViewRowHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeFavoriteRecyclerViewAdapter
                .RecipeFavoriteViewRowHolder holder, int position) {

            RecipeID RecipeId = RecipeIDs.get(position);
            holder.title.setText(RecipeId.getTitle());
            holder.bind(RecipeID);

        }

        @Override
        public int getItemCount() {
            return RecipeIDs.size();
        }

        public class FavoriteSongViewRowHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView title;
            ImageButton deleteBtn;

            public RecipeFavoriteViewRowHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                title = itemView.findViewById(R.id.recipeTitle);
                deleteBtn = itemView.findViewById(R.id.deleteBtn);

                RecipeIDDatabase db = Room.databaseBuilder(context.getApplicationContext(), RecipeIDDAO.class, "database-recipeIds").build();
                rDAO = db.rDAO();
                deleteBtn.setOnClickListener(v -> {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityRecipeFavorite.this);
                        builder.setMessage(getString(R.string.recipe_delete_warning) + title.getText())
                                .setTitle("Question:")
                                .setNegativeButton("No", (dialog, which) -> {
                                })
                                .setPositiveButton("Yes", (dialog, which) -> {
                                    // 4.
                                    RecipeID removedRecipeId = RecipeIDs.get(position);
                                    Executors.newSingleThreadExecutor().execute(() -> {
                                        rDAO.deleteRecipeID(removedRecipeId);
                                        runOnUiThread(() -> {
                                            RecipeIDs.remove(position);
                                            adapter.notifyItemRemoved(position);
                                            Snackbar.make(title, "You deleted recipe #" + position, Snackbar.LENGTH_LONG)
                                                    .setAction("Undo", clk2 -> {
                                                        RecipeIDs.add(position, removedRecipeId);
                                                        adapter.notifyItemInserted(position);
                                                        Executors.newSingleThreadExecutor().execute(() -> {
                                                            rDAO.insertRecipeID(removedRecipeId);
                                                        });
                                                    })
                                                    .show();
                                        });
                                    });
                                })
                                .create().show();
                    }
                });

                // Set click listener
                itemView.setOnClickListener(clk -> {
                    int position = getAbsoluteAdapterPosition();
                    RecipeID RecipeID = RecipeIDs.get(position);
                    Intent nextPage = new Intent(ActivityRecipeFavorite.this, ActivityRecipeIDDetail.class);
                    nextPage.putExtra("ingredients", RecipeID.getIngredient());
                    nextPage.putExtra("instruction", RecipeID.getInstruction());
                    nextPage.putExtra("picture", RecipeID.getPicture_medium());
                    nextPage.putExtra("recipe", RecipeID.getTitle());
                    nextPage.putExtra("id", RecipeID.getRecipeId());

                    startActivity(nextPage);
                });
            }

            public void bind(RecipeID RecipeID) {
                Picasso.get().load(RecipeID.getPicture_medium()).into(imageView);
            }
        }
    }
}


