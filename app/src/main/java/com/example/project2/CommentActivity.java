package com.example.project2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class CommentActivity extends AppCompatActivity {
    CommentAdapter commentAdapter;
    RecyclerView recyclerView;
    Button makeCommentBtn;
    EditText editText;
    Button backBtn;

    List<String> comment;

    String musicName;

    private View decorView;
    private int	uiOption;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

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
        musicName = getIntent.getStringExtra("musicName");

        editText = findViewById(R.id.editText);
        makeCommentBtn = findViewById(R.id.makeCommentBtn);
        backBtn = findViewById(R.id.backBtn);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commentAdapter = new CommentAdapter(getApplicationContext());

        makeCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentAdapter.setArrayData(editText.getText().toString());
                recyclerView.setAdapter(commentAdapter);

                Gson gson = new GsonBuilder().setLenient().create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getResources().getString(R.string.address))
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                RetrofitService service1 = retrofit.create(RetrofitService.class);
                Call<String> call = service1.postNewComment(musicName, editText.getText().toString());
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
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });



        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.address))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);
        Call<List<String>> getCommentDataCall = service1.getCommentData(musicName);
        getCommentDataCall.enqueue(new Callback<List<String>>(){
            @Override
            public void onResponse(Call<List<String>> getCommentDataCall, Response<List<String>> response){
                if(response.isSuccessful()){
                    List<String> result = response.body();

                    int size = result.size();

                    for(int i = 0; i < size; i++){
                        commentAdapter.setArrayData(result.get(i));
                        Log.d("Comment list : ", result.get(i));
                    }
                    recyclerView.setAdapter(commentAdapter);
                }
                else{
                    Log.d("MY TAG", "onResponse: 실패 "+String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<List<String>> getMusicDataCall, Throwable t){
                Log.d("MY TAG", "onFailure: "+t.getMessage());
            }
        });



//        commentAdapter.setArrayData("댓글1");
//        commentAdapter.setArrayData("댓글2");
//        commentAdapter.setArrayData("댓글3");
//        commentAdapter.setArrayData("댓글4");

//        recyclerView.setAdapter(commentAdapter);
    }
}
