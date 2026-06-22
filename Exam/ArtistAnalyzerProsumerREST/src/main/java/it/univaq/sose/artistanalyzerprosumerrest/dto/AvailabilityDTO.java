package it.univaq.sose.artistanalyzerprosumerrest.dto;

import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
import it.univaq.sose.artistanalyzerprosumerrest.model.StreamingService;


public class AvailabilityDTO extends AuditDTO{

    private Integer id;

    private Song song;

    private StreamingServiceDTO streamingService;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public StreamingServiceDTO getStreamingService() {
        return streamingService;
    }

    public void setStreamingService(StreamingServiceDTO streamingService) {
        this.streamingService = streamingService;
    }

    public AvailabilityDTO(Integer id, Song song, StreamingServiceDTO streamingService) {
        this.id = id;
        this.song = song;
        this.streamingService = streamingService;
    }
}
