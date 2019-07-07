package com.nyka.primedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder> {

    private Context mContext;
    private ArrayList<trending> mTrendingList;

    public TrendingAdapter(Context context, ArrayList<trending> trendingList) {
        mContext = context;
        mTrendingList = trendingList;
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.whatnow_item, viewGroup,false);
        return new TrendingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder trendingViewHolder, int i) {
            trending currentItem = mTrendingList.get(i);
            String trendingTitle = currentItem.getTrendingTitle();
            String trendingPoster =currentItem.getTrendingPoster();
            String trendingReleaseDate=currentItem.getTrendingReleaseDate();

            trendingViewHolder.mTitle.setText(trendingTitle);
            trendingViewHolder.mReleaseDate.setText(trendingReleaseDate);
        Glide.with(mContext).load(trendingPoster).into(trendingViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mTrendingList.size();
    }

    class TrendingViewHolder extends RecyclerView.ViewHolder{
         ImageView mImageView;
         TextView mTitle;
         TextView mReleaseDate;

        public TrendingViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.imTrendingPoster);
            mTitle=itemView.findViewById(R.id.lbTrendingTitle);
            mReleaseDate=itemView.findViewById(R.id.lbTrendingReleaseDate);
        }
    }

}
