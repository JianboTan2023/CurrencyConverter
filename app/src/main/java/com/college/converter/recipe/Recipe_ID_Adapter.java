package com.college.converter.recipe;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;

public class Recipe_ID_Adapter {

    public class Recipe_ID_Adapter extends RecyclerView.Adapter<Recipe_ID_Adapter.RecipeIdViewHolder> {

        private Context context;
        private ArrayList<RecipeId> RecipeId;
        private static OnItemClickListener listener;

        public Recipe_ID_Adapter(Context context, ArrayList<RecipeId> RecipeId) {
            this.context = context;
            this.RecipeId = RecipeId;
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

            RecipeId recipeId = RecipeId.get(position);
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
            TextView title, rank, duration;

            public RecipeIdViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                title     = itemView.findViewById(R.id.songTitle);
                rank      = itemView.findViewById(R.id.rank);
                duration  = itemView.findViewById(R.id.duration);

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
