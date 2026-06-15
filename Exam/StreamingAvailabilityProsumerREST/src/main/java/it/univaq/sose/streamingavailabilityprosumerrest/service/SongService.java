package it.univaq.sose.streamingavailabilityprosumerrest.service;

import it.univaq.sose.streamingavailabilityprosumerrest.model.Song;

import java.util.List;

public interface SongService {
    Song searchByID(Integer ID);
    List<Song> getAll();
}
