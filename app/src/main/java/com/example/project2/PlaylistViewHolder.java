package com.example.project2;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PlaylistViewHolder extends RecyclerView.ViewHolder {
    public ImageView album_image;
    public TextView text_music;
    public TextView text_singer;

    public PlaylistViewHolder(Context context, View itemView) {
        super(itemView);
        album_image = itemView.findViewById(R.id.album_image);
        text_music = itemView.findViewById(R.id.music_text);
        text_singer = itemView.findViewById(R.id.singer_text);
    }
}
