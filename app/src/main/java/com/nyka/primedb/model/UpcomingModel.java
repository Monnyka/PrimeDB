package com.nyka.primedb.model;

public class UpcomingModel {

    private String movieTitle;
    private String releaseDate;
    private String movieBanner;
    private String movieID;
    private String movieOverview;

    public UpcomingModel(String movieTitle, String releaseDate, String movieBanner, String id, String overView) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.movieBanner = movieBanner;
        this.movieID=id;
        movieOverview=overView;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMovieBanner() {
        return movieBanner;
    }

    public String getMovieID(){return movieID;}

    public String getMovieOverview(){
        return movieOverview;
    }
}
