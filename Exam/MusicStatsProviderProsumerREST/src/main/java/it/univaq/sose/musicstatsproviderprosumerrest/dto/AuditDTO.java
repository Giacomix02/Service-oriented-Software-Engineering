package it.univaq.sose.musicstatsproviderprosumerrest.dto;


import java.time.Instant;

public class AuditDTO {

    protected Instant createdAt;

    protected Instant updatedAt;

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
