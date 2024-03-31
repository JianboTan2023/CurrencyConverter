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

import com.college.converter.recipe.data.Recipe;

import java.util.ArrayList;

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
            View view = inflater.inflate(R.layout.deezer_recycleview_row,parent,false);
            return new RecipeAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeAdapter.MyViewHolder holder, int position) {
            Recipe recipe = recipes.get(position);
            Log.d("debug2", recipe.getRecipeName());
            holder.name.setText(recipes.get(position).getRecipeName());
            holder.ingredient.setText(recipes.get(position).getIngredient());
            holder.recipebind(recipe);
        }

        @Override
        public int getItemCount() {
            return recipes.size();//this recycler view is to know the number of items to display
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }


        public static class MyViewHolder extends RecyclerView.ViewHolder{
            TextView name, ingredient;
            ImageView imageView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                name = itemView.findViewById(R.id.recipeName);
                ingredient = itemView.findViewById(R.id.ingredient);


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
                Picasso.get().load(recipe.getRecipePicture_medium()).into(imageView);
            }

        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }
}
