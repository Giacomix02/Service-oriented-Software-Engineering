package it.univaq.sose.artistanalyzerprosumerrest.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.sose.artistanalyzerprosumerrest.dto.SongDTO;
import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
import it.univaq.sose.artistanalyzerprosumerrest.provider.MusicStatsDataProvider;


@Service
public class SongServiceImpl implements SongService {


    @Autowired
    private MusicStatsDataProvider songDataProvider;

    @Override
    public List<SongDTO> findAll() {
        return songDataProvider.getAllSongs();
    }

    @Override
    public SongDTO findById(int id) {
        return songDataProvider.getSongById(id);
    }

    @Override
    public List<SongDTO> findByName(String name) {

        List<SongDTO> filteredSongs = new ArrayList<SongDTO>();
        List<SongDTO> songs = songDataProvider.getAllSongs();
        for (SongDTO song : songs) {
            if (song.getName().contains(name)) {
                filteredSongs.add(song);
            }
        }
        return filteredSongs;
    }

    @Override
    public List<SongDTO> findByArtistName(String name) {
        return songDataProvider.getAllByArtist(name);
    }
    

}
