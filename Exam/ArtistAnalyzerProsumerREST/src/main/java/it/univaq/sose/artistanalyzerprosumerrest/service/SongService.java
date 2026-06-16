package it.univaq.sose.artistanalyzerprosumerrest.service;

import java.util.List;


import it.univaq.sose.artistanalyzerprosumerrest.model.Song;


public interface SongService {
    List<Song> findAll();
    Song findById(int id);
    Song findByName(String name);


    List<Song> findByArtistName(String name);
    
}
