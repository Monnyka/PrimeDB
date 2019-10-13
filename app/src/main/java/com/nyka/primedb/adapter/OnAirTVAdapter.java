package com.nyka.primedb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;
import com.nyka.primedb.R;
import com.nyka.primedb.model.OnAirTVModel;
import java.util.ArrayList;

public class OnAirTVAdapter extends RecyclerView.Adapter <OnAirTVAdapter.OnAirTVViewHolder>{
    private Context mContext;
    private ArrayList<OnAirTVModel> mOnAirTVList;

    public OnAirTVAdapter(Context mContext, ArrayList<OnAirTVModel> mOnAirTVList) {
        this.mContext = mContext;
        this.mOnAirTVList = mOnAirTVList;
    }

    @NonNull
    @Override
    public OnAirTVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.trending_item,viewGroup,false);
        return new OnAirTVViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OnAirTVViewHolder onAirTVViewHolder, int i) {
        OnAirTVModel currentItem=mOnAirTVList.get(i);

        String image=currentItem.getImgUrl();
        String title=currentItem.getTitle();
        String vote=currentItem.getVote();
        onAirTVViewHolder.mTitle.setText(title);
        onAirTVViewHolder.mVote.setText(vote);
        Glide.with(mContext).load(image).into(onAirTVViewHolder.mPoster);


    }

    @Override
    public int getItemCount() {
        return mOnAirTVList.size();
    }

    class OnAirTVViewHolder extends RecyclerView.ViewHolder{
            ImageView mPoster;
            TextView mTitle;
            TextView mVote;
        OnAirTVViewHolder(@NonNull View itemView) {
            super(itemView);
            mPoster=itemView.findViewById(R.id.imTrendingPoster);
            mTitle=itemView.findViewById(R.id.lbTrendingTitle);
            mVote=itemView.findViewById(R.id.lbGenre);
        }
    }
}
