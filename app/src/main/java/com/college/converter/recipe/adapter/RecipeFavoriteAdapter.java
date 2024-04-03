package com.college.converter.recipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.converter.R;
import com.college.converter.recipe.data.Recipe;
import com.college.converter.recipe.ui.RecipeDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
/**
 * @author Kelly Wu
 * @lab section 021
 * this is to hold data for favorite recipe
 */
public class RecipeFavoriteAdapter extends RecyclerView.Adapter<RecipeFavoriteAdapter.RecipeViewHolder> {
    private Context context;
    private List<Recipe> recipeList;

    /**
     * Construct of the adapter
     * @param context The current context.
     * @param recipeList list of recipes
     */
    public RecipeFavoriteAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    /**
     * RecipeViewHolder hold all the view components of recipes
     */
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        /**
         * Constructs the ViewHolder instance and binds the view components.
         */
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recipeTitle);
            imageView = itemView.findViewById(R.id.recipeImage);
        }
    }
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list, parent, false);
        return new RecipeViewHolder(view);
    }

    /**
     * This method sets up the title of the recipe,Picasso for asynchronous loading image
     * onclicklistener opens the recipe details
     * @param holder   should be updated to represent the data at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position)
    {
        Recipe recipe = recipeList.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        holder.itemView.setOnClickListener(clk -> {
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("recipe", recipe);
            intent.putExtra("source", RecipeDetailActivity.SOURCE_FAVORITE);
            context.startActivity(intent);
        });
        Picasso.get().load(recipe.getImageUrl()).into(holder.imageView);
    }


    /**
     * initialize total number of items held by the adapter.
     */
    @Override
    public int getItemCount() {
        return recipeList.size();
    }


}
