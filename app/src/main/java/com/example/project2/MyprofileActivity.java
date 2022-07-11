package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.project2.ImageLoadTask;

public class MyprofileActivity extends AppCompatActivity {
    Button backBtn;
    Button viewLikesBtn;
    TextView name;
    TextView recordText;
    ImageView profile;
    JSONArray likelist;

    String baseurl;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        String nicknameValue = UserData.getInstance().getNicknameData();
        String profileImageURL = UserData.getInstance().getProfileImageData();

        String record = UserData.getInstance().getRecordData();

        recordText = findViewById(R.id.recordText);
        recordText.setText(record + " 초");

        Intent getIntent = getIntent();
        baseurl = getResources().getString(R.string.address) + "musics/";
        likelist = new JSONArray();

        name = findViewById(R.id.name);
        profile = findViewById(R.id.profile);

        name.setText(nicknameValue);
        new ImageLoadTask(profileImageURL, profile).execute();

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        viewLikesBtn = findViewById(R.id.ViewLikesBtn);
        viewLikesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Gson gson = new GsonBuilder().setLenient().create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getResources().getString(R.string.address))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                RetrofitService service1 = retrofit.create(RetrofitService.class);

                Call<List<String>> call = service1.getLikeListData(UserData.getInstance().getIdData());

                call.enqueue(new Callback<List<String>>(){
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response){
                        if(response.isSuccessful()){
                            List<String> result = response.body();
                            Log.d("MY TAG", "onResponse: 성공, 결과\n"+result);

                            for(int i = 0; i < result.size(); i++){
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("name", result.get(i).replace(".wav", ""));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                likelist.put(jsonObject);
                            }
                            if(likelist.length() != 0){
                                Intent intent = new Intent(getApplicationContext(), LikeListActivity.class);
                                intent.putExtra("likeList", likelist.toString());
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "좋아요를 누른 노래가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
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
}
