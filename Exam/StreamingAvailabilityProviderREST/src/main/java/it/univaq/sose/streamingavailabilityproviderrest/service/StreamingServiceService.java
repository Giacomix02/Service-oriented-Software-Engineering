package it.univaq.sose.streamingavailabilityproviderrest.service;

import it.univaq.sose.streamingavailabilityproviderrest.model.StreamingService;

import java.util.List;

public interface StreamingServiceService {
    StreamingService findByID(Integer ID);
    StreamingService findByName(String name);
    List<StreamingService> getAll();
}
