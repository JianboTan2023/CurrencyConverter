package com.college.converter.recipe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.college.converter.recipe.data.RecipeID;

import java.util.ArrayList;

public class Recipe_ID_Adapter {

    public class Recipe_ID_Adapter extends RecyclerView.Adapter<Recipe_ID_Adapter.RecipeIdViewHolder> {

        private Context context;
        private ArrayList<RecipeID> recipeId;
        private static OnItemClickListener listener;

        public Recipe_ID_Adapter(Context context, ArrayList<RecipeID> recipeId) {
            this.context = context;
            this.recipeId = recipeId;
        }

        @NonNull
        @Override
        public Recipe_ID_Adapter.RecipeViewRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.deezer_track_recycleview_row, parent, false);

            return new Recipe_ID_Adapter.RecipeViewRowHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Recipe_ID_Adapter.RecipeViewRowHolder holder, int position) {

            recipeId recipeId = recipeId.get(position);
            holder.title.setText(recipeId.getTitle_short());
            holder.instruction.setText(recipeId.getInstruction());
            holder.bind(recipeId);

        }

        @Override
        public int getItemCount() {
            return recipeId.size();
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public static class RecipeIdViewHolder extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView title,instruction;

            public RecipeIdViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                title     = itemView.findViewById(R.id.recipeTitle);
                instruction = itemView.findViewById(R.id.instruction);

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

            public void bind(Track track) {
                Picasso.get().load(track.getPicture_medium()).into(imageView);
            }
        }
    }

}
