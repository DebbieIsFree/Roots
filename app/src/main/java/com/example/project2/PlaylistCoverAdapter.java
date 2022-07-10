package com.example.project2;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaylistCoverAdapter extends RecyclerView.Adapter<PlaylistCoverViewHolder> {
    private ArrayList<String> arrayList;
    Context context;

    public PlaylistCoverAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PlaylistCoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.playlist_cover_panel, parent, false);

        PlaylistCoverViewHolder playlistCoverViewholder = new PlaylistCoverViewHolder(context, view);

        return playlistCoverViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistCoverViewHolder holder, int position) {
        String playlistId = arrayList.get(position);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.address))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);
        Call<PlaylistData> call = service1.getPlaylistData(UserData.getInstance().getIdData());
        call.enqueue(new Callback<PlaylistData>(){
            @Override
            public void onResponse(Call<PlaylistData> call, Response<PlaylistData> response){
                if(response.isSuccessful()){
                    PlaylistData result = response.body();
                    holder.text_music.setText(result.getName());

                    String imageUrl = "";
                    if(result.getPlaylist().size() == 0){
                        imageUrl = context.getResources().getString(R.string.address) + "image/Blank.jpg";
                    }
                    else {
                        imageUrl = context.getResources().getString(R.string.address) + "image/" + result.getPlaylist().get(0) + ".jpg";
                    }
                    new ImageLoadTask(imageUrl, holder.album_image).execute();
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

        holder.album_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlaylistActivity.class);
                intent.putExtra("platlist_id", playlistId);
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });
        holder.text_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlaylistActivity.class);
                intent.putExtra("platlist_id", playlistId);
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // 데이터를 입력
    public void setArrayData(String playlistId) {
        arrayList.add(playlistId);
    }
}