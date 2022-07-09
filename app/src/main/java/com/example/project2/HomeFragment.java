package com.example.project2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    PlaylistCoverAdapter playlistCoverAdapter;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        playlistCoverAdapter = new PlaylistCoverAdapter(getContext());

        for(int i = 0; i < 50; i++){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("music", Integer.toString(i));
                jsonObject.put("singer", Integer.toString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            playlistCoverAdapter.setArrayData(jsonObject);
        }
        recyclerView.setAdapter(playlistCoverAdapter);

        Button button = (Button)view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getContext().getApplicationContext(), PlayingMusic.class);
                startActivity(intent);
            }
        });


        return view;
    }
}