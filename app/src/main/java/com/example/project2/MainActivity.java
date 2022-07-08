package com.example.project2;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    RankingFragment rankingFragment;
    RecommendationFragment recommendataionFragment;
    HomeFragment homeFragment;
    TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (TabLayout) findViewById(R.id.tabs);
        transaction = fragmentManager.beginTransaction();

        homeFragment = new HomeFragment();
        rankingFragment = new RankingFragment();
        recommendataionFragment = new RecommendationFragment();

        tabs.addTab(tabs.newTab().setText("Home"));
        tabs.addTab(tabs.newTab().setText("Ranking"));
        tabs.addTab(tabs.newTab().setText("Recommendation"));

        fragmentManager.beginTransaction().add(R.id.frame, homeFragment).commit();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("position","${position}");

                if(position == 0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, homeFragment).commit();
                }
                else if(position == 1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, rankingFragment).commit();
                }
                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, recommendataionFragment).commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.find_button:
                findButtonPushed();
                return true;
            case R.id.profile_button:
                profileButtonPushed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void findButtonPushed() {
        Intent intent = new Intent(getApplicationContext(), FindActivity.class);
        startActivity(intent);
    }

    public void profileButtonPushed() {
        Intent intent = new Intent(getApplicationContext(), FindActivity.class);
    }


    public TabLayout getTabs() {
        return tabs;
    }

}