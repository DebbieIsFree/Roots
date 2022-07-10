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
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("music-data")
    Call<MusicData> getMusicData(@Query("music_name") String music_name);

    @GET("is-music-like")
    Call<String> isMusicLike(@Query("kakao_id") String kakao_id,
                             @Query("music_name") String music_name);

    @GET("music-list")
    Call<List<String>> getMusicListData();

    @GET("playlist-list")
    Call<String> getPlaylistListData(@Query("kakao_id") String kakao_id);

    @FormUrlEncoded
    @POST("register")
    Call<String> register(@Field("kakao_id") String kakao_id,
                           @Field("profile_nickname") String profile_nickname,
                           @Field("profile_image") String profile_image);

    @FormUrlEncoded
    @PUT("like")
    Call<String> putLike(@Field("kakao_id") String kakao_id,
                                @Field("music_name") String music_name,
                                @Field("islike") String islike);

    @GET("/view-likes")
    Call<List<String>> getLikeListData(@Query("kakao_id") String kakao_id);
}
