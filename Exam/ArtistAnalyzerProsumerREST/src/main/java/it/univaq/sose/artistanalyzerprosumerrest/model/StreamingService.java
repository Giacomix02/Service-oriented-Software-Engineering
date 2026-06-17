package it.univaq.sose.artistanalyzerprosumerrest.model;

import java.util.List;

import it.univaq.sose.artistanalyzerprosumerrest.model.audit.DateAudit;
import jakarta.persistence.*;


@Entity
@Table(name= "Streaming_Service")

public class StreamingService extends DateAudit {
    private static final long serialVersionUID = -3246829878748726299L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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