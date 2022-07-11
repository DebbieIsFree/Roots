package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.sdk.user.UserApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    List<String> musicList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn = findViewById(R.id.btn2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(getApplicationContext())){
                    login();
                }
                else{
                    accountLogin();
                }
            }
        });
    }


//    @Override
    public void login(){
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void accountLogin() {
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void getUserInfo(){
        String TAG = "getUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError);
            } else {
                System.out.println("로그인 완료");
                Log.i(TAG, user.toString());
                {
                    Log.i(TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: "+user.getId() +
                            "\n이메일: "+user.getKakaoAccount().getEmail());
                }
            }
            Gson gson = new GsonBuilder().setLenient().create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.address))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            RetrofitService service1 = retrofit.create(RetrofitService.class);

            Call<String> call = service1.register(Long.toString(user.getId()),
                    String.valueOf(user.getProperties().get("nickname")),
                    String.valueOf(user.getProperties().get("profile_image")));

            UserData.getInstance().setIdData(Long.toString(user.getId()));
            UserData.getInstance().setNicknameData(user.getProperties().get("nickname"));
            UserData.getInstance().setProfileImageData(user.getProperties().get("profile_image"));
            UserData.getInstance().setRecordData("0");

            SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("id", Long.toString(user.getId()));
            editor.putString("nickname", user.getProperties().get("nickname"));
            editor.putString("profileImage", user.getProperties().get("profile_image"));
            editor.putString("record", "0");
            editor.commit();
            getPreferences(MODE_PRIVATE);

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

            Intent intent = new Intent(getApplicationContext(), MyprofileActivity.class);
            startActivity(intent);

            return null;
        });


    }
}