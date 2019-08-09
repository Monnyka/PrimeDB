package com.nyka.primedb.model;

public class trending {
    private String mTrendingTitle;
    private String mTrendingPoster;
    private String mTrendingReleaseDate;
    private String mTrendingID;
    private String mGenre;

    public trending(String trendingTitle, String trendingPoster, String trendingReleaseDate, String trendingID,String genre) {
        mTrendingTitle = trendingTitle;
        mTrendingPoster = trendingPoster;
        mTrendingReleaseDate = trendingReleaseDate;
        mTrendingID=trendingID;
        mGenre=genre;
    }

    public String getmGenre(){return mGenre;}

    public String getTrendingTitle() {
        return mTrendingTitle;
    }

    public String getTrendingPoster() {
        return mTrendingPoster;
    }

    public String getTrendingReleaseDate() {
        return mTrendingReleaseDate;
    }

    public String getTrendingID() {
        return mTrendingID;
    }
}
