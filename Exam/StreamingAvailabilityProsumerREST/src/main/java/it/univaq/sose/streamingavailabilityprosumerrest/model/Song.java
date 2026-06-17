package it.univaq.sose.streamingavailabilityprosumerrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.univaq.sose.streamingavailabilityprosumerrest.model.Audit.DateAudit;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name= "song")

public class Song extends DateAudit {
    private static final long serialVersionUID = -3246829878748726296L;

    @Id
    private Integer id;

    public Song() {}

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }


}
