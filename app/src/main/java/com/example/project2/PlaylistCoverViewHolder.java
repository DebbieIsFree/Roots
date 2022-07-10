package com.example.project2;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PlaylistCoverViewHolder extends RecyclerView.ViewHolder {
    public ImageView album_image;
    public TextView text_music;

    public PlaylistCoverViewHolder(Context context, View itemView) {
        super(itemView);
        album_image = itemView.findViewById(R.id.album_image);
        text_music = itemView.findViewById(R.id.music_text);
    }
}
