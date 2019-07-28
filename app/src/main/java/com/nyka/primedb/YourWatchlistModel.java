package com.nyka.primedb;

public class YourWatchlistModel {

    private String mImagePoster;
    private String mMovieTitle;
    private String mMovieYear;

    public YourWatchlistModel(String moviePoster, String movieTitle, String movieYear){

        mImagePoster=moviePoster;
        mMovieTitle=movieTitle;
        mMovieYear=movieYear;
    }


    public String getmImagePoster() {
        return mImagePoster;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public String getmMovieYear() {
        return mMovieYear;
    }
}
