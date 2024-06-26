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
// Define Track_Adapter class extending RecyclerView.Adapter and specifying ViewHolder type as TrackViewRowHolder
public class Track_Adapter extends RecyclerView.Adapter<Track_Adapter.TrackViewRowHolder> {

    private Context context;// Context to access application resources and operations
    private ArrayList<Track> tracks;// List to store Track objects
    private static OnItemClickListener listener;// Interface to handle item click events

    // Constructor to initialize the adapter's context and tracks list
    public Track_Adapter(Context context, ArrayList<Track> tracks) {
        this.context = context;
        this.tracks = tracks;
    }
    // Method to create ViewHolder objects, used to create new ViewHolder instances in the RecyclerView
    @NonNull
    @Override
    public Track_Adapter.TrackViewRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the layout for each item
        View view = inflater.inflate(R.layout.deezer_track_recycleview_row, parent, false);

        return new Track_Adapter.TrackViewRowHolder(view);
    }
    // Method to bind data to ViewHolder, used to bind track data to view elements within ViewHolder
    @Override
    public void onBindViewHolder(@NonNull Track_Adapter.TrackViewRowHolder holder, int position) {

        Track track = tracks.get(position);
        holder.title.setText(track.getTitle_short());
        holder.rank.setText(track.getRank());
        holder.duration.setText(track.getDuration());
        holder.bind(track);

    }
    // Method to get the total number of items in the dataset
    @Override
    public int getItemCount() {
        return tracks.size();
    }
    // Interface for handling item click events
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    // Method to set click listener for item
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    // ViewHolder inner class to represent the view for each item in the RecyclerView
    public static class TrackViewRowHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, rank, duration;
        // ViewHolder constructor to initialize view elements
        public TrackViewRowHolder(@NonNull View itemView) {
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
        // Method to bind track data to ImageView
        public void bind(Track track) {
            Picasso.get().load(track.getPicture_medium()).into(imageView);
        }
    }
}

