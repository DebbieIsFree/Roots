package com.example.project2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("music")
    Call<MusicData> getMusicData(@Query("musidId") String musicId);

    @GET("music-list")
    Call<List<String>> getMusicListData();
}
