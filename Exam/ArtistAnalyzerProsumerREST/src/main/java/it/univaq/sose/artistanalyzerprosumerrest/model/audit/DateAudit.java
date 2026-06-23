package it.univaq.sose.artistanalyzerprosumerrest.model.audit;

import java.io.Serializable;
import java.time.Instant;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public abstract class DateAudit implements Serializable {
	private static final long serialVersionUID = 8855349267095984755L;

	private Instant createdAt;


	private Instant updatedAt;

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

}