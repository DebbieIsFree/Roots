package com.example.project2;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PlaylistCoverViewHolder extends RecyclerView.ViewHolder {
    public ImageView album_image_1;
    public ImageView album_image_2;
    public ImageView album_image_3;
    public ImageView album_image_4;
    public TextView text_music;

    public PlaylistCoverViewHolder(Context context, View itemView) {
        super(itemView);
        album_image_1 = itemView.findViewById(R.id.album_image_1);
        album_image_2 = itemView.findViewById(R.id.album_image_2);
        album_image_3 = itemView.findViewById(R.id.album_image_3);
        album_image_4 = itemView.findViewById(R.id.album_image_4);
        text_music = itemView.findViewById(R.id.music_text);
    }
}
