package com.example.project2;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private ArrayList<String> arrayList;
    Context context;

    public CommentAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.comment_panel, parent, false);

        CommentViewHolder viewholder = new CommentViewHolder(context, view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        String comment = arrayList.get(position);
        holder.text_comment.setText(comment);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // 데이터를 입력
    public void setArrayData(String comment) {
        arrayList.add(comment);
    }
}