package it.univaq.sose.artistanalyzerprosumerrest.model;

import it.univaq.sose.artistanalyzerprosumerrest.model.audit.DateAudit;





public class Availability extends DateAudit {
    private static final long serialVersionUID = -3246829878748768308L;


    private Integer id;


    private Song song;


    private StreamingService streamingService;

    public Availability() {}

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Song getSong() { return song; }
    public void setSong(Song song) { this.song = song; }

    public StreamingService getStreamingService() { return streamingService; }
    public void setStreamingService(StreamingService streamingService) { this.streamingService = streamingService; }

}
