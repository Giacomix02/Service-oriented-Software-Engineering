package it.univaq.sose.streamingavailabilityprosumerrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.univaq.sose.streamingavailabilityprosumerrest.model.Audit.DateAudit;
import jakarta.persistence.*;

@Entity
@Table(name= "availability")

public class Availability extends DateAudit {
    private static final long serialVersionUID = -3246829878748768308L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_song", nullable = false)
    @JsonIgnoreProperties("availabilities")
    private Song song;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_streaming_service", nullable = false)
    @JsonIgnoreProperties("availabilities")
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
