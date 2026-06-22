package it.univaq.sose.streamingavailabilityproviderrest.dto;


import java.util.List;

public class AvailabilityDTO extends AuditDTO{

    private Integer id;

    private SongDTO song;

    private List<StreamingServiceDTO> streamingServices;

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

    public List<StreamingServiceDTO> getStreamingServices() {
        return streamingServices;
    }

    public void setStreamingServices(List<StreamingServiceDTO> streamingServices) {
        this.streamingServices = streamingServices;
    }

    public AvailabilityDTO(Integer id, SongDTO song, List<StreamingServiceDTO> streamingServices) {
        this.id = id;
        this.song = song;
        this.streamingServices = streamingServices;
    }
}
