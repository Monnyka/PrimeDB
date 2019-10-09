package com.nyka.primedb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nyka.primedb.R;
import com.nyka.primedb.model.OnAirTVModel;

import java.util.ArrayList;

public class OnAirTodayAdapter extends RecyclerView.Adapter<OnAirTodayAdapter.OnAirTodayViewHolder> {
    private Context mContext;
    private ArrayList<OnAirTVModel> mOnAirTodayList;

    public OnAirTodayAdapter(Context context, ArrayList<OnAirTVModel> onAirTodayList) {
        mContext=context;
        mOnAirTodayList=onAirTodayList;

    }

    @NonNull
    @Override
    public OnAirTodayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.trending_item,viewGroup,false);
        return new OnAirTodayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OnAirTodayViewHolder onAirTodayViewHolder, int i) {
        OnAirTVModel currentItem=mOnAirTodayList.get(i);
        String mTitle=currentItem.getTitle();
        String mEpisode=currentItem.getEpisode();
        String mPoster=currentItem.getImgUrl();
        onAirTodayViewHolder.mTitle.setText(mTitle);
        onAirTodayViewHolder.mEpisode.setText(mEpisode);
        Glide.with(mContext).load(mPoster).into(onAirTodayViewHolder.mImgUrl);

    }

    @Override
    public int getItemCount() {
        return mOnAirTodayList.size();
    }

    public class OnAirTodayViewHolder extends RecyclerView.ViewHolder{
             TextView mTitle;
             ImageView mImgUrl;
             TextView mEpisode;

        public OnAirTodayViewHolder(@NonNull View itemView) {
            super(itemView);
                mImgUrl=itemView.findViewById(R.id.imTrendingPoster);
                mTitle=itemView.findViewById(R.id.lbTrendingTitle);
                mEpisode=itemView.findViewById(R.id.lbGenre);

        }
    }

}
