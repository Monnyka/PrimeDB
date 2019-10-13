package com.nyka.primedb.model;

public class TrendingTVModel {

    private String mImgUrl;
    private String mTitle;
    private String mVote;

    public TrendingTVModel(String imgUrl, String title, String vote) {
        mImgUrl=imgUrl;
        mTitle=title;
        mVote=vote;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmVote() {
        return mVote;
    }
}
