package it.univaq.sose.artistanalyzerprosumerrest.dto.fromProviders;

import jakarta.persistence.*;

import java.util.List;

import it.univaq.sose.artistanalyzerprosumerrest.dto.AuditDTO;

public class SongDTOProvider extends AuditDTO{
    
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
