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
import com.nyka.primedb.model.UpcomingModel;

import java.util.ArrayList;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder> {

    private Context mContext;
    private ArrayList<UpcomingModel> mUpcomingList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnUpcomingClickListener(int position);
    }

    public void setOnClickListener(FragmentActivity listener){
//        mListener=listener;
    }

    public UpcomingAdapter(Context context,ArrayList<UpcomingModel> upcominglist) {
       mContext=context;
       mUpcomingList=upcominglist;
    }

    @NonNull
    @Override
    public UpcomingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.upcoming_main_item,viewGroup,false);
        return new UpcomingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingViewHolder upcomingViewHolder, int i) {
        UpcomingModel currentItem= mUpcomingList.get(i);
        String mTitle=currentItem.getMovieTitle();
        String mReleaseDate=currentItem.getReleaseDate();
        String mBanner=currentItem.getMovieBanner();
        String mReview=currentItem.getMovieOverview();

        upcomingViewHolder.mTextTitle.setText(mTitle);
        upcomingViewHolder.mTextReleaseDate.setText(mReleaseDate);
        upcomingViewHolder.mTextReview.setText(mReview);
        Glide.with(mContext).load(mBanner).into(upcomingViewHolder.mIvMovie);
    }

    @Override
    public int getItemCount() {
        return mUpcomingList.size();
    }

    public class UpcomingViewHolder extends RecyclerView.ViewHolder{
        ImageView mIvMovie;
        TextView mTextTitle;
        TextView mTextReleaseDate;
        TextView mTextReview;

        public UpcomingViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvMovie=itemView.findViewById(R.id.ivUpcomingBanner);
            mTextTitle=itemView.findViewById(R.id.lbUpcomingMovieTitle);
            mTextReleaseDate=itemView.findViewById(R.id.lbReleaseDate);
            mTextReview=itemView.findViewById(R.id.lbReview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.OnUpcomingClickListener(position);
                        }
                    }
                }
            });

        }

    }

}














