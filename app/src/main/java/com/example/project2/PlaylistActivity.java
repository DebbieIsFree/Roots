package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

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
    TextView playlistNameText;
    RankingAdapter rankingAdapter;
    Button playlistStartButton;
    Button playlistAddButton;
    Button playlistDeleteButton;
    ImageButton editPlaylistButton;
    String playlistId;

    private View decorView;
    private int	uiOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility( uiOption );

        Intent getIntent = getIntent();
        playlistId = getIntent.getStringExtra("playlist_id");

        playlistNameText = findViewById(R.id.playlist_name_text);

        recyclerView = findViewById(R.id.playlist_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rankingAdapter = new RankingAdapter(getApplicationContext());

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
                        rankingAdapter.setArrayData(result.getPlaylist().get(i));
                    }
                    recyclerView.setAdapter(rankingAdapter);
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

        playlistStartButton = (Button) findViewById(R.id.playlist_start_button);
        playlistStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                            Intent intent = new Intent(getApplicationContext(), PlayingMusic.class);
                            intent.putExtra("playlist_id", playlistId);
                            intent.putExtra("index", "0");
                            intent.putExtra("repeatMode", "repeatAll");
                            intent.putExtra("musicName", result.getPlaylist().get(0));
                            startActivity(intent);
                        }
                        else{
                            Log.d("MY TAG", "onResponse: 실패 " + String.valueOf(response.code()));
                        }
                    }
                    @Override
                    public void onFailure(Call<PlaylistData> call, Throwable t){
                        Log.d("MY TAG", "onFailure: "+t.getMessage());
                    }
                });

            }
        });

        playlistAddButton = (Button) findViewById(R.id.playlist_add_button);
        playlistAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectMusicActivity.class);
                intent.putExtra("playlist_id", playlistId);
                startActivity(intent);
            }
        });

        playlistDeleteButton = (Button) findViewById(R.id.playlist_delete_button);
        playlistDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder().setLenient().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getResources().getString(R.string.address))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                RetrofitService service1 = retrofit.create(RetrofitService.class);
                Call<String> call = service1.deletePlaylist(UserData.getInstance().getIdData(), playlistId);
                call.enqueue(new Callback<String>(){
                    @Override
                    public void onResponse(Call<String> call, Response<String> response){
                        if(response.isSuccessful()){
                            String result = response.body();
                            Log.d("MY TAG", "onResponse: 성공 " + result);
                        }
                        else{
                            Log.d("MY TAG", "onResponse: 실패 " + String.valueOf(response.code()));
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t){
                        Log.d("MY TAG", "onFailure: "+t.getMessage());
                    }
                });
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        editPlaylistButton = (ImageButton) findViewById(R.id.editPlaylistButton);
        editPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditPlaylistActivity.class);
                intent.putExtra("playlist_id", playlistId);
                startActivity(intent);
            }
        });
    }
}