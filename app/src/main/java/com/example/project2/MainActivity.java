package com.example.project2;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.project2.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    RankingFragment Rankingfragment;
    RecommendationFragment Recommendataionfragment;
    TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (TabLayout) findViewById(R.id.tabs);
        transaction = fragmentManager.beginTransaction();

        Rankingfragment = new RankingFragment();
        Recommendataionfragment = new RecommendationFragment();

        tabs.addTab(tabs.newTab().setText("Ranking"));
        tabs.addTab(tabs.newTab().setText("Recommendation"));

        fragmentManager.beginTransaction().add(R.id.frame, Rankingfragment).commit();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("position","${position}");

                if(position == 0){
//                    transaction.replace(R.id.frame, Rankingfragment, null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, Rankingfragment).commit();
                }
                else{
//                    transaction.replace(R.id.frame, Recommendataionfragment, null);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, Recommendataionfragment).commit();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO : tab의 상태가 선택되지 않음으로 변경.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // TODO : 이미 선택된 tab이 다시
            }
        });
    }

    public TabLayout getTabs() {
        return tabs;
    }

}