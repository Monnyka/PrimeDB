package com.nyka.primedb.model;

public class OnAirTVModel {

    private String imgUrl;
    private String title;
    private String vote;
    private String id;

    public OnAirTVModel(String imgUrl, String title, String vote, String id) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.vote = vote;
        this.id=id;
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

    public String getId() {
        return id;
    }
}
