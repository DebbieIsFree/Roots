package com.example.project2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;


public class RankingFragment extends Fragment {
    Activity activity;
    Context context;
    RankingPagerAdapter rankingPagerAdapter;
    ViewPager viewPager;
    CircleIndicator indicator;

    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        activity = getActivity();
        context = getContext();

        viewPager = view.findViewById(R.id.viewPager);
        rankingPagerAdapter = new RankingPagerAdapter(context);
        viewPager.setAdapter(rankingPagerAdapter);
        indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        return view;
    }
}
