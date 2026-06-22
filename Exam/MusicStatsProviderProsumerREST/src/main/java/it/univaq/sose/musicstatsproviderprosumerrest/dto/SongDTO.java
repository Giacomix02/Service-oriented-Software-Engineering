package it.univaq.sose.musicstatsproviderprosumerrest.dto;


public class SongDTO extends AuditDTO{
    private int id;

    private String name;

    private String description;

    private int views;

    private ArtistDTO artist;

    public SongDTO(int id, String name, String description, int views, ArtistDTO artist) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.views = views;
        this.artist = artist;
    }

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

    public ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }
}
