package it.univaq.sose.musicstatsproviderprosumerrest.service;

import java.util.List;

import it.univaq.sose.musicstatsproviderprosumerrest.model.Song;

public interface SongService {
    List<Song> findAll();
    Song findById(int id);
    
}
