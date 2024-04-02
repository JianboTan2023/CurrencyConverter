package com.college.converter.recipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.recyclerview.widget.RecyclerView;

import com.college.converter.R;
import com.college.converter.recipe.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kelly Wu
 * @date March 31
 * @lab section 021
 * this adapter class is for the firest request, enter the recipe name, such as salad, pasta,
 * a list of recipe will show up in recyclerview
 * it links with recyclerview_row_recipe
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Context context;
    private List<Recipe> recipeList;

    private RecipeDao recipeDao;

    /**
     * Constructs the RecipeAdapter with the provided context and recipe list.
     *
     * @param context    The current context.
     * @param recipeList The list of recipes to display.
     */
    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
        recipeDao = RecipeDatabase.getDbInstance(context).recipeDao();
    }

    /**
     * Called when RecyclerView needs a new {@link RecipeViewHolder} of the given type to represent
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new RecipeViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The RecipeViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        holder.itemView.setOnClickListener(clk -> {
            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra("source", RecipeDetailsActivity.SOURCE_LIST);
            intent.putExtra("recipeId", recipe.recipeId);
            intent.putExtra("title", recipe.title);
            intent.putExtra("image_url", recipe.imageUrl);
            intent.putExtra("summary", recipe.summary);
            intent.putExtra("source_url", recipe.sourceUrl);
            context.startActivity(intent);
        });
        Picasso.get().load(recipe.getImageUrl()).into(holder.imageView);
        // Set other fields accordingly
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    /**
     * Provides a reference to the type of views that the adapter uses.
     */
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        /**
         * Initializes the RecipeViewHolder with the provided itemView.
         *
         * @param itemView The View for the Recipe item.
         */
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recipeTitle);
            imageView = itemView.findViewById(R.id.recipeImage);
        }
    }
}




