package com.college.converter.recipe.adapter;

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

import java.util.ArrayList;

public class RecipeFavoriteAdapter extends RecyclerView.Adapter<RecipeFavoriteAdapter.TrackViewRowHolder>{

    private Context context;
    private ArrayList<RecipeID> RecipeID;
    private static OnItemClickListener listener;

    public RecipeFavoriteAdapter(Context context, ArrayList<RecipeID> RecipeId) {
        this.context = context;
        this.RecipeID = RecipeId;
    }

    @NonNull
    @Override
    public RecipeFavoriteAdapter.TrackViewRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_favorite_recipe, parent, false);

        return new RecipeFavoriteAdapter.TrackViewRowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeFavoriteAdapter.TrackViewRowHolder holder, int position) {

        RecipeID RecipeID = RecipeID.get(position);
        holder.title.setText(RecipeID.getTitle_short());
        holder.bind(RecipeID);

    }

    @Override
    public int getItemCount() {
        return RecipeID.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class TrackViewRowHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;

        public TrackViewRowHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title     = itemView.findViewById(R.id.recipeTitle);

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
