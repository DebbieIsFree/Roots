package com.example.project2;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Call<List<String>> getPlaylistListData(@Query("kakao_id") String kakao_id);

    @GET("playlist")
    Call<PlaylistData> getPlaylistData(@Query("playlist_id") String playlist_id);

    @FormUrlEncoded
    @POST("register")
    Call<String> register(@Field("kakao_id") String kakao_id,
                           @Field("profile_nickname") String profile_nickname,
                           @Field("profile_image") String profile_image);

    @FormUrlEncoded
    @POST("new-playlist")
    Call<String> postNewPlaylist(@Field("kakao_id") String kakao_id);

    @FormUrlEncoded
    @PUT("like")
    Call<String> putLike(@Field("kakao_id") String kakao_id,
                                @Field("music_name") String music_name,
                                @Field("islike") String islike);

    @FormUrlEncoded
    @PUT("music-to-playlist")
    Call<String> putMusicToPlaylist(@Field("playlist_id") String playlist_id,
                                    @Field("music_name") String music_name );

    @GET("view-likes")
    Call<List<String>> getLikeListData(@Query("kakao_id") String kakao_id);

    @FormUrlEncoded
    @PUT("new-comment")
    Call<String> postNewComment(@Field("music_name") String music_name,
                                @Field("comment") String comment);

    @FormUrlEncoded
    @PUT("playlist-name")
    Call<String> putPlaylistName(@Field("playlist_id") String playlist_id,
                                @Field("playlist_name") String playlist_name);

    @FormUrlEncoded
    @PUT("music-from-playlist")
    Call<String> deleteMusicFromPlaylist(@Field("playlist_id") String playlist_id,
                                         @Field("music_name") String music_name );

    @GET("comment-data")
    Call<List<String>> getCommentData(@Query("music_name") String music_name);
}
