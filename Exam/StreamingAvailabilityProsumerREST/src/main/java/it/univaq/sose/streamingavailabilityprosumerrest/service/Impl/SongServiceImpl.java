package it.univaq.sose.streamingavailabilityprosumerrest.service.Impl;

import it.univaq.sose.streamingavailabilityprosumerrest.model.Song;
import it.univaq.sose.streamingavailabilityprosumerrest.repository.SongRepository;
import it.univaq.sose.streamingavailabilityprosumerrest.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService{

    @Autowired
    protected SongRepository repository;

    @Override
    public Song searchByID(Integer ID) {
        return repository.findById(ID).orElse(null);
    }

    @Override
    public List<Song> getAll() {
        return repository.findAll();
    }
}
