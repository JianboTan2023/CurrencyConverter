package com.college.converter.song.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.college.converter.R;
import com.college.converter.song.data.Track;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


    public class FavoriteSong_Adapter extends RecyclerView.Adapter<FavoriteSong_Adapter.TrackViewRowHolder> {

        private Context context;
        private ArrayList<Track> tracks;
        private static OnItemClickListener listener;

        public FavoriteSong_Adapter(Context context, ArrayList<Track> tracks) {
            this.context = context;
            this.tracks = tracks;
        }

        @NonNull
        @Override
        public FavoriteSong_Adapter.TrackViewRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.deerzer_favorite_song_recycleview_row, parent, false);

            return new FavoriteSong_Adapter.TrackViewRowHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FavoriteSong_Adapter.TrackViewRowHolder holder, int position) {

            Track track = tracks.get(position);
            holder.title.setText(track.getTitle_short());
            holder.rank.setText(track.getRank());
            holder.duration.setText(track.getDuration());
            holder.bind(track);

        }

        @Override
        public int getItemCount() {
            return tracks.size();
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        public static class TrackViewRowHolder extends RecyclerView.ViewHolder{

            ImageView imageView;
            TextView title, rank, duration;

            public TrackViewRowHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView2);
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


