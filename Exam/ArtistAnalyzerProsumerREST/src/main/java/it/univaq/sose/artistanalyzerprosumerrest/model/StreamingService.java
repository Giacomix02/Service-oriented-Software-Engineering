package it.univaq.sose.artistanalyzerprosumerrest.model;

import it.univaq.sose.artistanalyzerprosumerrest.model.audit.DateAudit;




public class StreamingService extends DateAudit {
    private static final long serialVersionUID = -3246829878748726299L;


    private Integer id;


    private String name;

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