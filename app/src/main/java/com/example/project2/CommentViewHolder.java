package com.example.project2;

import android.widget.TextView;

import org.w3c.dom.Text;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    public TextView text_comment;

    public CommentViewHolder(Context context, View itemView) {
        super(itemView);
        text_comment = itemView.findViewById(R.id.commentTextView);
    }
}