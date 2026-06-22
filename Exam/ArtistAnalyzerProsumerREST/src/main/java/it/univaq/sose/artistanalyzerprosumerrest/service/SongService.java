package it.univaq.sose.artistanalyzerprosumerrest.service;

import java.util.List;


import it.univaq.sose.artistanalyzerprosumerrest.dto.SongDTO;
import it.univaq.sose.artistanalyzerprosumerrest.model.Song;


public interface SongService {
    List<SongDTO> findAll();
    SongDTO findById(int id);
    List<SongDTO> findByName(String name);
    List<SongDTO> findByArtistName(String name);
    
}
