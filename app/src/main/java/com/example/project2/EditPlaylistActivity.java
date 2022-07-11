package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditPlaylistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    EditText playlistNameText;
    EditPlaylistAdapter editPlaylistAdapter;
    Button finishEditPlaylistButton;
    String playlistId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_playlist);

        Intent getIntent = getIntent();
        playlistId = getIntent.getStringExtra("playlist_id");

        playlistNameText = (EditText)findViewById(R.id.playlist_name_text);

        recyclerView = findViewById(R.id.playlist_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        editPlaylistAdapter = new EditPlaylistAdapter(getApplicationContext(), playlistId);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.address))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);

        Call<PlaylistData> call = service1.getPlaylistData(playlistId);

        call.enqueue(new Callback<PlaylistData>(){
            @Override
            public void onResponse(Call<PlaylistData> call, Response<PlaylistData> response){
                if(response.isSuccessful()){
                    PlaylistData result = response.body();
                    Log.d("MY TAG", "onResponse: 성공, 결과\n"+result);

                    playlistNameText.setText(result.getName());

                    for(int i = 0; i < result.getPlaylist().size(); i++){
                        editPlaylistAdapter.setArrayData(result.getPlaylist().get(i));
                    }
                    recyclerView.setAdapter(editPlaylistAdapter);
                }
                else{
                    Log.d("MY TAG", "onResponse: 실패 "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<PlaylistData> call, Throwable t){
                Log.d("MY TAG", "onFailure: "+t.getMessage());
            }
        });

        finishEditPlaylistButton = (Button) findViewById(R.id.finishEditPlaylistButton);
        finishEditPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder().setLenient().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getResources().getString(R.string.address))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                RetrofitService service1 = retrofit.create(RetrofitService.class);
                Call<String> call = service1.putPlaylistName(playlistId, playlistNameText.getText().toString());
                call.enqueue(new Callback<String>(){
                    @Override
                    public void onResponse(Call<String> call, Response<String> response){
                        if(response.isSuccessful()){
                            String result = response.body();
                            Log.d("MY TAG", "onResponse: 성공, 결과\n"+result);
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

                Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);
                intent.putExtra("playlist_id", playlistId);
                startActivity(intent);
            }
        });
    }
}