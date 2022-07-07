package com.example.project2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RankingFragment Rankingfragment = new RankingFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.frame, Rankingfragment).commit();
        RecommendationFragment Recommendationfragment = new RecommendationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, Recommendationfragment).commit();
    }
}

