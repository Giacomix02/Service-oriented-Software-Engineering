package it.univaq.sose.streamingavailabilityproviderrest.service.Impl;

import it.univaq.sose.streamingavailabilityproviderrest.model.StreamingService;
import it.univaq.sose.streamingavailabilityproviderrest.repository.StreamingServiceRepository;
import it.univaq.sose.streamingavailabilityproviderrest.service.StreamingServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StreamingServiceServiceImpl implements StreamingServiceService {

    @Autowired
    protected StreamingServiceRepository repository;

    @Override
    public StreamingService findByID(Integer ID) {
        return repository.findById(ID).orElse(null);
    }

    @Override
    public StreamingService findByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    @Override
    public List<StreamingService> getAll() {
        return repository.findAll();
    }
}
