package com.nyka.primedb.model;

public class UpcomingModel {

    private String movieTitle;
    private String releaseDate;
    private String movieBanner;

    public UpcomingModel(String movieTitle, String releaseDate, String movieBanner) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.movieBanner = movieBanner;
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
}
