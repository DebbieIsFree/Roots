package com.example.project2;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("music")
    Call<MusicData> getMusicData(@Query("musidId") String musicId);

    @GET("music-list")
    Call<List<String>> getMusicListData();

    @POST("register")
    Call<List<String>> register(@Query("profile_nickname") String profile_nickname,
                                @Query("profile_image") String profile_image,
                                @Query("account_email") String account_email,
                                @Query("gender") String gender,
                                @Query("age_range") String age_range ,
                                @Query("birthday") String birthday);

    @POST("check-like")
    Call<List<String>> postLike(@Query("name") String name,
                                @Query("singer") String singer,
                                @Query("year") String year);

}
