package com.example.project2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LikeListActivity extends AppCompatActivity {
    RankingAdapter rankingAdapter;
    RecyclerView recyclerView;
    JSONArray jsonArray;

    ArrayList<String> nameList = new ArrayList<String>();

    private View decorView;
    private int	uiOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likelist);

        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility( uiOption );

        recyclerView = findViewById(R.id.likelistrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rankingAdapter = new RankingAdapter(getApplicationContext());

        Intent intent = getIntent();
        String likelistArray= intent.getStringExtra("likeList");

        try {
            jsonArray = new JSONArray(likelistArray);
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                nameList.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

                    for(int i = 0; i < nameList.size(); i++){
                        rankingAdapter.setArrayData(nameList.get(i));
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

//        likeAdapter.setArrayData("댓글1");
//        likeAdapter.setArrayData("댓글2");
//        likeAdapter.setArrayData("댓글3");
//        likeAdapter.setArrayData("댓글4");
//
//        recyclerView.setAdapter(likeAdapter);
    }
}
