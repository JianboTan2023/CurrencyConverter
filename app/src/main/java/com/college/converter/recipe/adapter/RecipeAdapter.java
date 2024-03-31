package com.college.converter.recipe.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.converter.R;
import com.college.converter.recipe.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author Kelly Wu
 * @date March 31
 * @lab section 021
 * this adapter class is for the firest request, enter the recipe name, such as salad, pasta,
 * a list of recipe will show up in recyclerview
 * it links with recyclerview_row_recipe
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder>{


        Context context;
        ArrayList<Recipe> recipes;
        private static OnItemClickListener listener;
        public RecipeAdapter(Context context, ArrayList<Recipe> recipes){
            this.context = context;
            this.recipes = recipes;
        }
        @NonNull
        @Override
        public RecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recyclerview_row_recipe,parent,false);
            return new RecipeAdapter.MyViewHolder(view);
        }

    /**
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
        @Override
        public void onBindViewHolder(@NonNull RecipeAdapter.MyViewHolder holder, int position) {
            Recipe recipe = recipes.get(position);
            Log.d("get entered random recipe name (general)", recipe.getRecipeName());
            holder.name.setText(recipes.get(position).getRecipeName());
            holder.ingredient.setText(recipes.get(position).getIngredient());
            holder.recipebind(recipe);
        }

    /**
     *
     * @return the number of items to display
     */
        @Override
        public int getItemCount() {
            return recipes.size();
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

    /**
     * initiate viewholder with variables
     */
        public static class MyViewHolder extends RecyclerView.ViewHolder{
            TextView name, title, ingredient;
            ImageView imageView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                name = itemView.findViewById(R.id.recipeName);
                ingredient = itemView.findViewById(R.id.ingredient);
                title = itemView.findViewById(R.id.recipeTitle);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && listener != null) {
                            listener.onItemClick(position);
                        }
                    }
                });
            }


            public void recipebind(Recipe recipe) {
                Picasso.get().load(recipe.getRecipePicture_small()).into(imageView);
            }

        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

