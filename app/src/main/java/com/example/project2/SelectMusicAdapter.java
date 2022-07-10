package com.example.project2;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectMusicAdapter extends RecyclerView.Adapter<SelectMusicViewHolder> {
    private ArrayList<String> arrayList;
    Context context;
    String playlistId;

    public SelectMusicAdapter(Context context, String playlistId) {
        this.context = context;
        arrayList = new ArrayList<>();
        this.playlistId = playlistId;
    }

    @NonNull
    @Override
    public SelectMusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.select_music_panel, parent, false);

        SelectMusicViewHolder viewholder = new SelectMusicViewHolder(context, view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectMusicViewHolder holder, int position) {
        String musicName = arrayList.get(position);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.address))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);

        Call<MusicData> getMusicDataCall = service1.getMusicData(musicName);

        getMusicDataCall.enqueue(new Callback<MusicData>(){
            @Override
            public void onResponse(Call<MusicData> getMusicDataCall, Response<MusicData> response){
                if(response.isSuccessful()){
                    MusicData result = response.body();

                    System.out.println(result.getName());

                    String name = result.getName();
                    String singer = result.getSinger();

                    String imageUrl = context.getResources().getString(R.string.address) + "image/" + name + ".jpg";
                    new ImageLoadTask(imageUrl, holder.album_image).execute();
                    holder.text_music.setText(name);
                    holder.text_singer.setText(singer);
                }
                else{
                    Log.d("MY TAG", "onResponse: 실패 "+String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<MusicData> getMusicDataCall, Throwable t){
                Log.d("MY TAG", "onFailure: "+t.getMessage());
            }
        });

        holder.album_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder().setLenient().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(context.getResources().getString(R.string.address))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                RetrofitService service1 = retrofit.create(RetrofitService.class);
                Call<String> call = service1.putMusicToPlaylist(playlistId, holder.text_music.getText().toString());
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

                Intent intent = new Intent(v.getContext(), PlaylistActivity.class);
                intent.putExtra("playlist_id", playlistId);
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });
        holder.text_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.text_singer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // 데이터를 입력
    public void setArrayData(String musicName) {
        arrayList.add(musicName);
    }
}