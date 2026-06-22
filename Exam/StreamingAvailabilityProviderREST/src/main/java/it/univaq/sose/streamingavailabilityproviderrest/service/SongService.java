package it.univaq.sose.streamingavailabilityproviderrest.service;

import it.univaq.sose.streamingavailabilityproviderrest.model.Song;

import java.util.List;

public interface SongService {
    Song searchByID(Integer ID);
    List<Song> getAll();
}
