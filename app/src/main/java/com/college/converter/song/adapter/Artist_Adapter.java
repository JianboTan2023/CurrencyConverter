package com.college.converter.song.adapter;

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
import com.college.converter.song.data.Artist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Artist_Adapter extends RecyclerView.Adapter<Artist_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Artist> artists;
    private static OnItemClickListener listener;
    public Artist_Adapter(Context context, ArrayList<Artist> artists){
        this.context = context;
        this.artists = artists;
    }
    @NonNull
    @Override
    public Artist_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.deezer_recycleview_row,parent,false);
        return new Artist_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Artist_Adapter.MyViewHolder holder, int position) {
        Artist artist = artists.get(position);
        Log.d("debug2", artist.getArtistName());
        holder.name.setText(artists.get(position).getArtistName());
        holder.album.setText(artists.get(position).getArtistNb_album());
        holder.fan.setText(artists.get(position).getArtistNb_fan());
        holder.artistbind(artist);
    }

    @Override
    public int getItemCount() {
        return artists.size();//the recycler view just wants to know the number of items you want display
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, album, fan;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            name = itemView.findViewById(R.id.artistName);
            album = itemView.findViewById(R.id.album);
            fan = itemView.findViewById(R.id.fans);

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


        public void artistbind(Artist artist) {
            Picasso.get().load(artist.getArtistPicture_medium()).into(imageView);
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
