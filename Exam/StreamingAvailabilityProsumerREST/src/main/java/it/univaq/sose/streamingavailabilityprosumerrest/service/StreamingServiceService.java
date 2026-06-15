package it.univaq.sose.streamingavailabilityprosumerrest.service;

import it.univaq.sose.streamingavailabilityprosumerrest.model.StreamingService;

import java.util.List;

public interface StreamingServiceService {
    StreamingService findByID(Integer ID);
    StreamingService findByName(String name);
    List<StreamingService> getAll();
}
