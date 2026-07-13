package it.univaq.sose.ticketresellerproviderrest.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Event {

    private int id;
    private int eventGlobalId;
    private String name;
    private String artistName;
    private String location;
    private String description;

    public Event() {}

    public Event(int id, int eventGlobalId, String name, String artistName,
                 String location, String description) {
        this.id = id;
        this.eventGlobalId = eventGlobalId;
        this.name = name;
        this.artistName = artistName;
        this.location = location;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEventGlobalId() { return eventGlobalId; }
    public void setEventGlobalId(int eventGlobalId) { this.eventGlobalId = eventGlobalId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}