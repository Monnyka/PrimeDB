package com.nyka.primedb.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> imageUrl;
    public ViewPagerAdapter(Context context, ArrayList<String> imageUrl){
        this.context=context;
        this.imageUrl=imageUrl;
    }
    @Override
    public int getCount() {
        return imageUrl.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView ImageView= new ImageView(context);
        Glide.with(context).load(imageUrl.get(position)).centerCrop().into(ImageView);
        container.addView(ImageView);
        return ImageView;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
