package com.example.project2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.databinding.FragmentRecommendationBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class RecommendationFragment extends Fragment {

    // Add RecyclerView member
    private RecyclerView recyclerView;
    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

        // Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new Adapter(getActivity().getApplicationContext());

        for(int i = 0; i < 50; i++){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("music", Integer.toString(i));
                jsonObject.put("singer", Integer.toString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter.setArrayData(jsonObject);
        }
        recyclerView.setAdapter(adapter);

        return view;
    }

}