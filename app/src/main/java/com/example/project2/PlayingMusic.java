package com.example.project2;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.renderscript.Sampler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.project2.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayingMusic extends AppCompatActivity {
    private Button btn;
    private Button getMusicListButton;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initalStage = true;
    SeekBar seekBar;

    String url = "http://192.249.18.200:80/musics/No_Roots.wav"; // 서버 음악파일 경로

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_music);

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        progressDialog = new ProgressDialog(this);

        btn = (Button) findViewById(R.id.audioStreamBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!playPause){
                    btn.setText("Pause Streaming");
                    if(initalStage){
                        new Player().execute(url);
                    } else {
                        if(!mediaPlayer.isPlaying()){
                            mediaPlayer.start();
                        }
                    }
                    playPause = true;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    playClicked(seekBar, mediaPlayer);

                } else {
                    btn.setText("Launch Streaming");
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    }
                    playPause = false;
                }
            }
        });

        getMusicListButton = (Button) findViewById(R.id.get_music_list_button);
        getMusicListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder().setLenient().create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.249.18.200:80/")
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
        });

    }

    public void playClicked(@NonNull SeekBar seekBar1, @NonNull MediaPlayer mp) {
        seekBar1.setMax(mp.getDuration());  // 음악의 총 길이를 시크바 최대값에 적용
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)  // 사용자가 시크바를 움직이면
                    mp.seekTo(progress);   // 재생위치를 바꿔준다(움직인 곳에서의 음악재생)
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        mp.start();
        new Thread(new Runnable(){  // 쓰레드 생성
            @Override
            public void run() {
                while(mp.isPlaying()){  // 음악이 실행중일때 계속 돌아가게 함
                    try{
                        Thread.sleep(1000); // 1초마다 시크바 움직이게 함
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    // 현재 재생중인 위치를 가져와 시크바에 적용
                    seekBar1.setProgress(mp.getCurrentPosition());
                }
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer != null){
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    class Player extends AsyncTask<String, Void, Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Buffering...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;
            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initalStage = true;
                        playPause = false;
                        btn.setText("Launch Streaming");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                Log.e("MyAudioStreamingApp",e.getMessage());
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(progressDialog.isShowing()){
                progressDialog.cancel();
            }
            mediaPlayer.start();
            initalStage = false;
        }
    }
}
