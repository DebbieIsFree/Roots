package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaylistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RankingAdapter rankingAdapter;
    Button playlistStartButton;
    JSONArray playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        playlist = new JSONArray();

        recyclerView = findViewById(R.id.playlist_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rankingAdapter = new RankingAdapter(getApplicationContext());

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
                        JSONObject jsonObject = new JSONObject();
                        try {
                            playlist.put(result.get(i).replace(".wav", ""));
                            jsonObject.put("music", result.get(i).replace(".wav", ""));
                            jsonObject.put("singer", Integer.toString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        rankingAdapter.setArrayData(jsonObject);
                    }
                    recyclerView.setAdapter(rankingAdapter);

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

        playlistStartButton = (Button) findViewById(R.id.playlist_start_button);
        playlistStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), PlayingMusic.class);
                intent.putExtra("mode", "playlist");
                intent.putExtra("playlist", playlist.toString());
                startActivity(intent);
            }
        });
    }
}