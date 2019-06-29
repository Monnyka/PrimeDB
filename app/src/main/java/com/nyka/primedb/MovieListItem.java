package com.nyka.primedb;

public class MovieListItem {

    private String mImageUrl;
    private String mMovieTitle;
    private String mMovieRate;
    private String mMovieReleaseDate;

    public MovieListItem(String imageUrl, String movieTitle, String movieRate, String movieReleaseDate){
         mImageUrl = imageUrl;
         mMovieTitle= movieTitle;
         mMovieRate=movieRate;
         mMovieReleaseDate=movieReleaseDate;
 }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public String getMovieRate() {
        return mMovieRate;
    }

    public String getMovieReleaseDate() {
        return mMovieReleaseDate;
    }
}
