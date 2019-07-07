package com.nyka.primedb;

public class trending {
    private String mTrendingTitle;
    private String mTrendingPoster;
    private String mTrendingReleaseDate;

    public trending(String trendingTitle, String trendingPoster, String trendingReleaseDate) {
        mTrendingTitle = trendingTitle;
        mTrendingPoster = trendingPoster;
        mTrendingReleaseDate = trendingReleaseDate;
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

}
