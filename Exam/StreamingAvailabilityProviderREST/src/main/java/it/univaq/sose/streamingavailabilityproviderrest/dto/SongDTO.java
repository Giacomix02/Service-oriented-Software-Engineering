package it.univaq.sose.streamingavailabilityproviderrest.dto;

import java.util.List;

public class SongDTO extends AuditDTO{
    private int id;

    public SongDTO(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
