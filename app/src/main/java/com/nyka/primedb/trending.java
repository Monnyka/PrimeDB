package com.nyka.primedb;

public class trending {
    private String mTrendingTitle;
    private String mTrendingPoster;
    private String mTrendingReleaseDate;
    private String mTrendingID;

    public trending(String trendingTitle, String trendingPoster, String trendingReleaseDate, String trendingID) {
        mTrendingTitle = trendingTitle;
        mTrendingPoster = trendingPoster;
        mTrendingReleaseDate = trendingReleaseDate;
        mTrendingID=trendingID;
    }

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
