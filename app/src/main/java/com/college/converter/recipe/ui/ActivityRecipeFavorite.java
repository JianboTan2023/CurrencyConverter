package com.college.converter.recipe.ui;

/**
 * @author Kelly Wu
 * @section 021
 */

import android.content.Context;
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


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.college.converter.R;
import com.college.converter.recipe.adapter.RecipeFavoriteAdapter;
import com.college.converter.recipe.data.RecipeID;
import com.college.converter.recipe.data.RecipeIDDAO;
import com.college.converter.recipe.data.RecipeIDDatabase;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ActivityRecipeFavorite extends AppCompatActivity {
    RecipeFavoriteRecyclerViewAdapter adapter;
    ArrayList<RecipeID> recipeIDs = new ArrayList<>();
    RecipeIDDAO rDAO;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe);

        Toolbar toolbar = findViewById(R.id.recipeToolBar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.RecipeIDRecyclerView);

        RecipeIDDatabase db = Room.databaseBuilder(getApplicationContext(), RecipeIDDatabase.class,
                "database-recipeId").build();
        rDAO = db.RecipeIDDAO();
        if (recipeIDs.size() == 0) {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                recipeIDs.addAll(rDAO.getAllRecipeIDs()); //Once you get the data from database
                runOnUiThread(() -> {
                    adapter = new RecipeFavoriteRecyclerViewAdapter(this, recipeIDs);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                });
            });
        }
    }





    class RecipeFavoriteRecyclerViewAdapter extends RecyclerView
            .Adapter<RecipeFavoriteRecyclerViewAdapter.RecipeFavoriteViewRowHolder> {

        private Context context;
        private ArrayList<RecipeID> RecipeIDs;
        RecipeIDDAO rDAO;

        public RecipeFavoriteRecyclerViewAdapter(Context context, ArrayList<RecipeID> RecipeIDs) {
            this.context = context;
            this.RecipeIDs = RecipeIDs;
        }

        @NonNull
        @Override
        public RecipeFavoriteViewRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recyclerview_favorite_recipe, parent, false);

            return new RecipeFavoriteViewRowHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeFavoriteRecyclerViewAdapter
                .RecipeFavoriteViewRowHolder holder, int position) {

            RecipeID recipeID = RecipeIDs.get(position);
            holder.title.setText(recipeID.getTitle());

            holder.bind(recipeID);

        }

        @Override
        public int getItemCount() {
            return recipeIDs.size();
        }

        public class RecipeFavoriteViewRowHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView title, ingredient;
            ImageButton deleteBtn;

            public RecipeFavoriteViewRowHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                title = itemView.findViewById(R.id.recipeTitle);
                deleteBtn = itemView.findViewById(R.id.deleteBtn);

                RecipeIDDatabase db = Room.databaseBuilder(context.getApplicationContext(), RecipeIDDatabase.class, "database-recipeIds").build();
                rDAO = db.RecipeIDDAO();
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
                    RecipeID recipeID = RecipeIDs.get(position);
                    Intent nextPage = new Intent(ActivityRecipeFavorite.this, ActivityRecipeIDDetail.class);
                    nextPage.putExtra("summary", recipeID.getSummary());
                    nextPage.putExtra("sourceUrl", recipeID.getSourceUrl());
                    nextPage.putExtra("picture", recipeID.getPicture());
                    nextPage.putExtra("recipe", recipeID.getTitle());
                    nextPage.putExtra("id", recipeID.getRecipeId());

                    startActivity(nextPage);
                });
            }

            public void bind(RecipeID RecipeID) {
                Picasso.get().load(RecipeID.getPicture()).into(imageView);
            }
        }
    }
}


