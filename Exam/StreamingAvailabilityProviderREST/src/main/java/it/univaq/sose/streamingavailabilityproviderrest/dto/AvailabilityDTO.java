package it.univaq.sose.streamingavailabilityproviderrest.dto;


public class AvailabilityDTO extends AuditDTO{

    private Integer id;

    private SongDTO song;

    private StreamingServiceDTO streamingService;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SongDTO getSong() {
        return song;
    }

    public void setSong(SongDTO song) {
        this.song = song;
    }

    public StreamingServiceDTO getStreamingService() {
        return streamingService;
    }

    public void setStreamingService(StreamingServiceDTO streamingService) {
        this.streamingService = streamingService;
    }

    public AvailabilityDTO(Integer id, SongDTO song, StreamingServiceDTO streamingService) {
        this.id = id;
        this.song = song;
        this.streamingService = streamingService;
    }
}
