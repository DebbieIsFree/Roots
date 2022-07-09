package com.example.project2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Account;
import com.kakao.util.exception.KakaoException;

public class LoginActivity extends AppCompatActivity {
    // 세션 콜백 구현
//    private ISessionCallback sessionCallback = new ISessionCallback() {
//        @Override
//        public void onSessionOpened() {
//            Log.i("KAKAO_SESSION", "로그인 성공");
//        }
//
//        @Override
//        public void onSessionOpenFailed(KakaoException exception) {
//            Log.e("KAKAO_SESSION", "로그인 실패", exception);
//        }
//    };

    com.kakao.usermgmt.LoginButton kakaoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        kakaoBtn = findViewById(R.id.login_button);
        Button btn = findViewById(R.id.btn2);

        Log.d("kakaologin" ,"kakao login0");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("kakaologin" ,"kakao login0");
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(getApplicationContext())){
                    login();
                }
                else{
                    accountLogin();
                    Log.d("kakaologin", "kakao login0");
                }
            }
        });
//        kakaoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("kakaologin" ,"kakao login0");
//                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(getApplicationContext())){
//                    login();
//                }
//                else{
//                    accountLogin();
//                    Log.d("kakaologin", "kakao login0");
//                }
//            }
//        });


//
//        if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(this)){
//            login();
//        }
//        else{
//            accountLogin();
//        }

//         세션 콜백 등록
//        Session.getCurrentSession().addCallback(sessionCallback);
    }
//    protected void onDestroy() {
//        super.onDestroy();
//
//        // 세션 콜백 삭제
//        Session.getCurrentSession().removeCallback(sessionCallback);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        // 카카오톡|스토리 간편로그인 실행 결과를 받아서 SDK로 전달
//        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
//            return;
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }


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
            Log.d("kakaologin", "kakao login0");
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
                Account user1 = user.getKakaoAccount();
                System.out.println("사용자 계정" + user1);
            }
            return null;
        });
    }
}