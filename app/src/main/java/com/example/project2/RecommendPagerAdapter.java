package com.example.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;


public class RecommendPagerAdapter extends PagerAdapter {
//    private int[] images = {R.drawable.pic0, R.drawable.pic1};
    private LayoutInflater layoutInflater;
    private Context context;
    private List<MusicData> musicData;

    public RecommendPagerAdapter(Context context, List<MusicData> musicData){
        this.context = context;
        this.musicData = musicData;
    }

    @Override
    public int getCount() {return musicData.size();}

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == ((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_recommendation, container, false);
        ImageView imageView = view.findViewById(R.id.image);

        String imageUrl = context.getResources().getString(R.string.address) + "image/" + musicData.get(position).getName() + ".jpg";

        new ImageLoadTask(imageUrl, imageView).execute();

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.invalidate();
    }
}
