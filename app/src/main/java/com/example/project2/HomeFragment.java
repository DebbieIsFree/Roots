package com.example.project2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    PlaylistCoverAdapter playlistCoverAdapter;
    Button addPlaylistButton;
    ArrayList<String> playlistList;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        addPlaylistButton = (Button) view.findViewById(R.id.add_playlist_button);
        addPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserData.getInstance().getIdData() == null){
                    Toast.makeText(getActivity().getApplicationContext(), "로그인을 먼저 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Gson gson = new GsonBuilder().setLenient().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getResources().getString(R.string.address))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                RetrofitService service1 = retrofit.create(RetrofitService.class);
                Call<String> call = service1.postNewPlaylist(UserData.getInstance().getIdData());
                call.enqueue(new Callback<String>(){
                    @Override
                    public void onResponse(Call<String> call, Response<String> response){
                        if(response.isSuccessful()){
                            String result = response.body();
                            Log.d("MY TAG", "onResponse: 성공 "+result);
                        }
                        else{
                            Log.d("MY TAG", "onResponse: 실패 "+String.valueOf(response.code()));
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t){
                        Log.d("MY TAG", "onFailure: "+t.getMessage());
                    }
                });
            }
        });

        if (UserData.getInstance().getIdData() == null){
            return view;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        playlistCoverAdapter = new PlaylistCoverAdapter(getContext());

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.address))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);
        Call<List<String>> call = service1.getPlaylistListData(UserData.getInstance().getIdData());
        call.enqueue(new Callback<List<String>>(){
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response){
                if(response.isSuccessful()){
                    List<String> result = response.body();
                    for(int i = 0; i < result.size(); i++){
                        playlistCoverAdapter.setArrayData(result.get(i));
                    }
                    recyclerView.setAdapter(playlistCoverAdapter);
                }
                else{
                    Log.d("MY TAG", "onResponse: 실패 "+String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t){
                Log.d("MY TAG", "onFailure: "+t.getMessage());
            }
        });

        return view;
    }
}