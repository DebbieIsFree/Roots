package com.example.project2;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView text_music;
    public TextView text_singer;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        text_music = itemView.findViewById(R.id.music_text);
        text_singer = itemView.findViewById(R.id.singer_text);
    }
}