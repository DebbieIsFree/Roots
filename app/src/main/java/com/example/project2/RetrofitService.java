package com.example.project2;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("music")
    Call<MusicData> getMusicData(@Query("musidId") String musicId);

    @GET("music-list")
    Call<List<String>> getMusicListData();

    @FormUrlEncoded
    @POST("register")
    Call<Integer> register(@Field("profile_nickname") String profile_nickname,
                                @Field("profile_image") String profile_image);

    @POST("check-like")
    Call<List<String>> postLike(@Query("name") String name,
                                @Query("singer") String singer,
                                @Query("year") String year);

}
