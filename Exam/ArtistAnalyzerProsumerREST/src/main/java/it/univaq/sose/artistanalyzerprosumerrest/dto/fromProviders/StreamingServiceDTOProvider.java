package it.univaq.sose.artistanalyzerprosumerrest.dto.fromProviders;

import it.univaq.sose.artistanalyzerprosumerrest.dto.AuditDTO;

public class StreamingServiceDTOProvider extends AuditDTO{

    
    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
