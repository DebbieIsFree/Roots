package com.example.project2;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayingMusic extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_music);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.10.5.152:443/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);

        Call<MusicData> call = service1.getMusicData("1");

        call.enqueue(new Callback<MusicData>(){
            @Override
            public void onResponse(Call<MusicData> call, Response<MusicData> response){
                if(response.isSuccessful()){
                    MusicData result = response.body();
                    Log.d("MY TAG", "onResponse: 성공, 결과\n"+result.getName()+result.getSinger()+Integer.toString(result.getYear()));
                }
                else{
                    Log.d("MY TAG", "onResponse: 실패 "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<MusicData> call, Throwable t){
                Log.d("MY TAG", "onFailure: "+t.getMessage());
            }
        });

    }

}