package it.univaq.sose.musicstatsproviderprosumerrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.sose.musicstatsproviderprosumerrest.model.Artist;
import it.univaq.sose.musicstatsproviderprosumerrest.repository.ArtistRepository;

public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;


    @Override
    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    @Override
    public Artist findById(Integer id) {
        return artistRepository.getReferenceById(id);
    }

    @Override
    public Artist findByName(String name) {
        return artistRepository.findByName(name);
    }
    
    
}
