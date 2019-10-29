package com.nyka.primedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nyka.primedb.R;
import com.nyka.primedb.model.trending;

import java.util.ArrayList;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder> {

    private Context mContext;
    private ArrayList<trending> mTrendingList;
    private onClickItemListener mListener;

    public interface onClickItemListener{

        void onItemClick(int position);
    }

    public void setOnClickListener(FragmentActivity listener){
//        mListener=listener;
    }

    public TrendingAdapter(Context context, ArrayList<trending> trendingList) {
        mContext = context;
        mTrendingList = trendingList;
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.trending_item, viewGroup,false);
        return new TrendingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder trendingViewHolder, int i) {
            trending currentItem = mTrendingList.get(i);
            String trendingTitle = currentItem.getTrendingTitle();
            String trendingPoster =currentItem.getTrendingPoster();
            String trendingReleaseDate=currentItem.getTrendingReleaseDate();
            String trendingGenre=currentItem.getmGenre();
            //String trendingID=currentItem.getTrendingID();


            trendingViewHolder.mTitle.setText(trendingTitle);
            trendingViewHolder.mReleaseDate.setText(trendingReleaseDate);
            trendingViewHolder.mGenre.setText(trendingGenre);
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
         TextView mGenre;


        public TrendingViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.imTrendingPoster);
            mTitle=itemView.findViewById(R.id.lbTrendingTitle);
            mReleaseDate=itemView.findViewById(R.id.lbTrendingReleaseDate);
            mGenre=itemView.findViewById(R.id.lbGenre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(mListener !=null) {

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

}
