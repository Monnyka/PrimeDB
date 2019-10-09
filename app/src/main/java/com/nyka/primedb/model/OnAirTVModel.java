package com.nyka.primedb.model;

public class OnAirTVModel {

    private String imgUrl;
    private String title;
    private String episode;

    public OnAirTVModel(String imgUrl, String title, String episode) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.episode = episode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getEpisode() {
        return episode;
    }
}
