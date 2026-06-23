package it.univaq.sose.artistanalyzerprosumerrest.model;

import java.util.List;

import it.univaq.sose.artistanalyzerprosumerrest.model.audit.DateAudit;



public class Song extends DateAudit{
    

    private int id;


    private String name;

    private String description;


    private int views;


    private Artist artist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}


