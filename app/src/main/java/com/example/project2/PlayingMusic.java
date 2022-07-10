package com.example.project2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayingMusic extends AppCompatActivity {
    private Button btn;
    private Button getMusicListButton;
    private Button backButton;
    private Button viewCommentBtn;
    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private boolean initalStage = true;
    SeekBar seekBar;

    TextView nameText;
    TextView singerText;

    Switch LikeSwitch;

    String baseurl; // 서버 음악파일 경로
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_music);

        baseurl = "http://192.249.18.200:80/musics/";

        Intent getIntent = getIntent();
        url = baseurl + getIntent.getStringExtra("musicName") + ".wav";

        System.out.println(url);


        nameText = (TextView) findViewById(R.id.name_text);
        singerText = (TextView) findViewById(R.id.singer_text);
        nameText.setText(getIntent.getStringExtra("musicName"));
        singerText.setText(getIntent.getStringExtra("singerName"));

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

        backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    try{
                        Thread.sleep(500); // 1초마다 시크바 움직이게 함
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
                playPause = false;

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("caller", getIntent.getStringExtra("caller"));
                startActivity(intent);
            }
        });

        LikeSwitch = (Switch) findViewById(R.id.LikeSwitch);
        LikeSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder().setLenient().create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.249.18.200:80/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                RetrofitService service1 = retrofit.create(RetrofitService.class);

                TextView name = (TextView) findViewById(R.id.name_text);
                TextView singer = (TextView) findViewById(R.id.singer_text);


                Call<List<String>> call = service1.postLike(name.getText().toString(), singer.getText().toString(), "");

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

        viewCommentBtn = (Button) findViewById(R.id.comment);
        viewCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                startActivity(intent);
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
                        Thread.sleep(500); // 1초마다 시크바 움직이게 함
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
