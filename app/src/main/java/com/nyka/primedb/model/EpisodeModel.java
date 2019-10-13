package com.nyka.primedb.model;

public class EpisodeModel {

    private String episodeTitle;
    private String episodeRelease;
    private String episodeOverall;

    public EpisodeModel(String episodeTitle, String episodeRelease, String episodeOverall) {
        this.episodeTitle = episodeTitle;
        this.episodeRelease = episodeRelease;
        this.episodeOverall = episodeOverall;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public String getEpisodeRelease() {
        return episodeRelease;
    }

    public String getEpisodeOverall() {
        return episodeOverall;
    }
}
