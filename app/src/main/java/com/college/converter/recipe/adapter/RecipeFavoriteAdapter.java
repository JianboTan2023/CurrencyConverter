package com.college.converter.recipe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.college.converter.R;
import com.college.converter.recipe.data.RecipeID;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeFavoriteAdapter extends RecyclerView.Adapter<RecipeFavoriteAdapter.RecipeIDViewRowHolder>{

    private Context context;
    private ArrayList<RecipeID> RecipeIDs;
    private static OnItemClickListener listener;

    public RecipeFavoriteAdapter(Context context, ArrayList<RecipeID> RecipeIDs) {
        this.context = context;
        this.RecipeIDs = RecipeIDs;
    }
    @Override
    public void onBindViewHolder(@NonNull RecipeFavoriteAdapter.RecipeIDViewRowHolder holder, int position) {

        RecipeID RecipeID = RecipeIDs.get(position);
        holder.title.setText(RecipeID.getTitle());
        holder.ingredient.setText(RecipeID.getIngredient());

        holder.bind(RecipeID);

    }

    @NonNull
    @Override
    public RecipeFavoriteAdapter.RecipeIDViewRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_favorite_recipe, parent, false);

        return new RecipeFavoriteAdapter.RecipeIDViewRowHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getBindingAdapterPosition()} which
     * will have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */

    @Override
    public int getItemCount() {
        return RecipeIDs.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class RecipeIDViewRowHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, ingredient;

        /**
         * initialize RecipeIDViewRowHolder
         * @param itemView display items for favorite recipe
         */
        public RecipeIDViewRowHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title     = itemView.findViewById(R.id.recipeTitle);
            ingredient = itemView.findViewById(R.id.ingredient);

            /**
             * set up click listener
             */
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

        /**
         * Picasso is used to display photos
         * @param RecipeID calls the picture_medium
         */
        public void bind(RecipeID RecipeID) {
            Picasso.get().load(RecipeID.getPicture_big()).into(imageView);
        }
    }
}
