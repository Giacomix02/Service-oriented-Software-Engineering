package it.univaq.sose.artistanalyzerprosumerrest.service;

import java.util.List;

import it.univaq.sose.artistanalyzerprosumerrest.dto.ArtistDTO;
import it.univaq.sose.artistanalyzerprosumerrest.model.Artist;

public interface ArtistService {
    List<ArtistDTO> findAll();
    ArtistDTO findById(int id);
    ArtistDTO findByName(String name);
}
