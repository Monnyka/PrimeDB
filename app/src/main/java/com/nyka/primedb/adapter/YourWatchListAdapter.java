package com.nyka.primedb.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;
import com.nyka.primedb.R;
import com.nyka.primedb.model.YourWatchlistModel;

import java.util.ArrayList;

public class YourWatchListAdapter extends RecyclerView.Adapter<YourWatchListAdapter.YourWatchListViewHolder> {
    private Context mContext;
    private ArrayList<YourWatchlistModel> mYourWatchList;

    public YourWatchListAdapter(Context context, ArrayList<YourWatchlistModel> yourWatchList){
        mContext=context;
        mYourWatchList=yourWatchList;
    }

    @NonNull
    @Override
    public YourWatchListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mContext).inflate(R.layout.yourwatchlist_item, viewGroup,false);
        
        return new YourWatchListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull YourWatchListViewHolder yourWatchListViewHolder, int i) {
        YourWatchlistModel currentItem = mYourWatchList.get(i);

        String imageUrl=currentItem.getmImagePoster();
        String movieTitle=currentItem.getmMovieTitle();
        String movieYear=currentItem.getmMovieYear();

        yourWatchListViewHolder.mMovieTitle.setText(movieTitle);
        yourWatchListViewHolder.mYear.setText(movieYear);
        Glide.with(mContext).load(imageUrl).into(yourWatchListViewHolder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mYourWatchList.size();
    }

    public class YourWatchListViewHolder extends RecyclerView.ViewHolder{
        ImageView  mImageView;
        TextView mMovieTitle;
        TextView mYear;
        CardView mCardView;

        public YourWatchListViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.iv_YourWatchList_Poster);
            mMovieTitle=itemView.findViewById(R.id.lbWatchListTitle);
            mYear=itemView.findViewById(R.id.lbWatchListYear);
            mCardView=itemView.findViewById(R.id.cd_YourWatchList);
        }
    }
}
