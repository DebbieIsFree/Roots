package com.example.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;


public class RecommendPagerAdapter extends PagerAdapter {
    private int[] images = {R.drawable.pic0, R.drawable.pic1};
    private LayoutInflater layoutInflater;
    private Context context;

    public RecommendPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {return images.length;}

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == ((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_recommendation, container, false);
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageResource(images[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.invalidate();
    }
}
