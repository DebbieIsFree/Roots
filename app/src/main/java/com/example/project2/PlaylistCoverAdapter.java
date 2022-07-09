package com.example.project2;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlaylistCoverAdapter extends RecyclerView.Adapter<PlaylistCoverViewHolder> {
    private ArrayList<JSONObject> arrayList;
    Context context;

    public PlaylistCoverAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PlaylistCoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.playlist_cover_panel, parent, false);

        PlaylistCoverViewHolder playlistCoverViewholder = new PlaylistCoverViewHolder(context, view);

        return playlistCoverViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistCoverViewHolder holder, int position) {
        JSONObject jsonData = arrayList.get(position);
        try {
            holder.text_music.setText(jsonData.getString("music"));
            holder.text_singer.setText(jsonData.getString("singer"));
        } catch (JSONException e) {System.out.println();}

        //전화번호부 클릭했을 때 반응

        holder.text_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlaylistActivity.class);
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });
        holder.text_singer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlaylistActivity.class);
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // 데이터를 입력
    public void setArrayData(JSONObject jsonData) {
        arrayList.add(jsonData);
    }
}