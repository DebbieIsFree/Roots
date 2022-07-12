package com.example.project2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecommendationFragment extends Fragment {
    Activity activity;
    Context context;
    RecommendPagerAdapter recommendPagerAdapter;
    ViewPager viewPager;
    CircleIndicator indicator;

    int[] randomNum = new int[5];
    Random random = new Random();

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);
        viewPager = view.findViewById(R.id.viewPager);
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for(int i = 0; i < Integer.parseInt(getResources().getString(R.string.number_of_music)); i++){
            arr.add(i);
        }
        for(int i = 0; i < 5; i++){
            int n = random.nextInt(Integer.parseInt(getResources().getString(R.string.number_of_music)) - i);
            randomNum[i] = arr.get(n);
            arr.remove(n);
        }

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.address))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitService service1 = retrofit.create(RetrofitService.class);

        Call<List<MusicData>> call = service1.randomdMusic(randomNum);

        call.enqueue(new Callback<List<MusicData>>(){
            @Override
            public void onResponse(Call<List<MusicData>> call, Response<List<MusicData>> response){
                if(response.isSuccessful()){
                    List<MusicData> result = response.body();

                    recommendPagerAdapter = new RecommendPagerAdapter(getContext(), result);

                    Log.d("MY TAG", "onResponse: 성공, 결과\n"+result);
                    viewPager.setAdapter(recommendPagerAdapter);
                    indicator = view.findViewById(R.id.indicator);
                    indicator.setViewPager(viewPager);
                }
                else{
                    Log.d("MY TAG", "onResponse: 실패 "+String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<List<MusicData>> call, Throwable t){
                Log.d("MY TAG", "onFailure: "+t.getMessage());
            }
        });

        return view;
    }
}

