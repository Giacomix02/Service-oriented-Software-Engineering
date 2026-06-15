package it.univaq.sose.streamingavailabilityprosumerrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.univaq.sose.streamingavailabilityprosumerrest.model.Audit.DateAudit;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name= "Song")

public class Song extends DateAudit {
    private static final long serialVersionUID = -3246829878748726296L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relation towards Aviability
    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities;

    public Song() {}

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public List<Availability> getAvailabilities() { return availabilities; }
    public void setAvailabilities(List<Availability> availabilities) { this.availabilities = availabilities; }

}
