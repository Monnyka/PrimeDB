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

public class MovieListAdapter extends RecyclerView.Adapter <MovieListAdapter.MovieViewHolder>{

    private Context mContext;
    private ArrayList<MovieListItem> mMovieList;

    public MovieListAdapter (Context context, ArrayList<MovieListItem> movieList){
        mContext=context;
        mMovieList=movieList;

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.movie_item,viewGroup, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {

        MovieListItem currentItem =mMovieList.get(i);

        String imageUrl=currentItem.getImageUrl();
        String movieTitle=currentItem.getMovieTitle();
        String movieRate=currentItem.getMovieRate();
        String movieRelease=currentItem.getMovieReleaseDate();


        movieViewHolder.mTextViewTitle.setText(movieTitle);
        movieViewHolder.mTextViewRate.setText(movieRate);
        movieViewHolder.mTextViewRelease.setText(movieRelease);
        Glide.with(mContext).load(imageUrl).centerInside().into(movieViewHolder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextViewTitle;
        TextView mTextViewRate;
        TextView mTextViewRelease;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
        mImageView=itemView.findViewById(R.id.ivMovieItem);
        mTextViewTitle=itemView.findViewById(R.id.txt_MovieTitle);
        mTextViewRate=itemView.findViewById(R.id.txt_Rate);
        mTextViewRelease=itemView.findViewById(R.id.txt_releaseDate);
        }
    }

}
