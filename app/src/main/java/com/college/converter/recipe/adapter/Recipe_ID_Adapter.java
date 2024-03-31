package com.college.converter.recipe.adapter;

/**
 * @author Kelly Wu
 * @lab section 021
 * this RecipeID adaptor is to hold Recipe ID received from the first request
 * it links with recycleview_recipeid_row
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.converter.R;
import com.college.converter.recipe.data.RecipeID;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



    public class Recipe_ID_Adapter extends RecyclerView.Adapter<Recipe_ID_Adapter.RecipeIDViewRowHolder> {

        private final Context context;
        private final ArrayList<RecipeID> RecipeIDs;
        private static OnItemClickListener listener;

        public Recipe_ID_Adapter(Context context, ArrayList<RecipeID> RecipeIDs) {
            this.context = context;
            this.RecipeIDs = RecipeIDs;
        }

        @NonNull
        @Override
        public Recipe_ID_Adapter.RecipeIDViewRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycleview_recipeid_row, parent, false);

            return new Recipe_ID_Adapter.RecipeIDViewRowHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Recipe_ID_Adapter.RecipeIDViewRowHolder holder, int position) {

            RecipeID RecipeID = RecipeIDs.get(position);
            holder.title.setText(RecipeID.getTitle());
            holder.ingredient.setText(RecipeID.getIngredient());
            holder.bind(RecipeID);

        }

        @Override
        public int getItemCount() {
            return RecipeIDs.size();
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            Recipe_ID_Adapter.listener = listener;
        }

        public static class RecipeIDViewRowHolder extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView title,ingredient, receipeId;

            public RecipeIDViewRowHolder(@NonNull View itemView) {
                super(itemView);
                receipeId = itemView.findViewById(R.id.recipeId);
                imageView = itemView.findViewById(R.id.imageView);
                title = itemView.findViewById(R.id.textView);
                ingredient = itemView.findViewById(R.id.textView4);

                // Set click listener
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

            public void bind(RecipeID RecipeID) {
                Picasso.get().load(RecipeID.getPicture_medium()).into(imageView);
            }
        }
    }

}
