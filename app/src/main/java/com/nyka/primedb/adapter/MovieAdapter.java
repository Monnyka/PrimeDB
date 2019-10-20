package com.nyka.primedb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nyka.primedb.R;
import com.nyka.primedb.model.MovieItem;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter <MovieAdapter.MovieViewHolder>{

    private Context mContext;
    private ArrayList <MovieItem> mMovieList;

    public MovieAdapter(FragmentActivity context, ArrayList<MovieItem> MovieList){
            mContext=context;
            mMovieList=MovieList;

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cast_list_item, viewGroup, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {

        MovieItem currentItem = mMovieList.get(i);
        String imageUrl=currentItem.getmImageUrl();
        String castName=currentItem.getmCastName();
        String charraterName=currentItem.getmCharacter();

        movieViewHolder.mCastName.setText(castName);
        movieViewHolder.mCharrater.setText(charraterName);
        Glide.with(mContext).load(imageUrl).fitCenter().into(movieViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mCastName;
        TextView mCharrater;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView=itemView.findViewById(R.id.im_cast_image);
            mCastName=itemView.findViewById(R.id.lbCastName);
            mCharrater=itemView.findViewById(R.id.lbCharacter);

        }
    }



}
