package it.univaq.sose.artistanalyzerprosumerrest.model;

import java.util.List;

import it.univaq.sose.artistanalyzerprosumerrest.model.audit.DateAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "song")
public class Song extends DateAudit{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "song_id")
    private int id;
    
    @Transient
    private List<StreamingService> streamingServices;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int views;

    @ManyToOne
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

    public List<StreamingService> getStreamingServices() {
        return streamingServices;
    }

    public void setStreamingServices(List<StreamingService> streamingServices) {
        this.streamingServices = streamingServices;
    }

}
