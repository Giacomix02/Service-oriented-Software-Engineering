package it.univaq.sose.artistanalyzerprosumerrest.dto;

import java.util.List;



public class AvailabilityDTO extends AuditDTO{

    private SongDTO song;

    private List<StreamingServiceDTO> streamingService;
    
    public SongDTO getSong() {
        return song;
    }

    public void setSong(SongDTO song) {
        this.song = song;
    }

    public List<StreamingServiceDTO> getStreamingService() {
        return streamingService;
    }

    public void setStreamingService(List<StreamingServiceDTO> streamingService) {
        this.streamingService = streamingService;
    }

    
}
