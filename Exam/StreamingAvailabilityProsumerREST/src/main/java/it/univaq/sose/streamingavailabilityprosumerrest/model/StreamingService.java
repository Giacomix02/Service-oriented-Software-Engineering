package it.univaq.sose.streamingavailabilityprosumerrest.model;

import it.univaq.sose.streamingavailabilityprosumerrest.model.Audit.DateAudit;
import jakarta.persistence.*;

@Entity
@Table(name= "streaming_service")

public class StreamingService extends DateAudit {
    private static final long serialVersionUID = -3246829878748726299L;

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    public StreamingService() {}

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}