package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectMusicActivity extends AppCompatActivity {
    // Add RecyclerView member
    private RecyclerView recyclerView;
    SelectMusicAdapter selectMusicAdapter;

    String playlistId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_music);

        // Add the following lines to create RecyclerView
        Intent getIntent = getIntent();
        playlistId = getIntent.getStringExtra("playlist_id");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        selectMusicAdapter = new SelectMusicAdapter(getApplicationContext(), playlistId);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.address))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);
        Call<List<String>> call = service1.getMusicListData();
        call.enqueue(new Callback<List<String>>(){
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response){
                if(response.isSuccessful()){
                    List<String> result = response.body();
                    Log.d("MY TAG", "onResponse: 성공, 결과\n"+result);

                    for(int i = 0; i < result.size(); i++){
                        selectMusicAdapter.setArrayData(result.get(i).replace(".wav", ""));
                    }
                    recyclerView.setAdapter(selectMusicAdapter);

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
    }
}