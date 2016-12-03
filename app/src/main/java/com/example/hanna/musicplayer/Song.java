package com.example.hanna.musicplayer;

/**
 * Created by Hanna on 03.12.2016.
 */

public class Song {

    private String url;
    private String title;
    private String performer;

    public Song(String url, String title, String performer){
        this.url = url;
        this.title = title;
        this.performer = performer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }
}
