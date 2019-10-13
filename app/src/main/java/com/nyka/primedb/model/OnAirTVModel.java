package com.nyka.primedb.model;

public class OnAirTVModel {

    private String imgUrl;
    private String title;
    private String vote;

    public OnAirTVModel(String imgUrl, String title, String vote) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.vote = vote;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getVote() {
        return vote;
    }
}
