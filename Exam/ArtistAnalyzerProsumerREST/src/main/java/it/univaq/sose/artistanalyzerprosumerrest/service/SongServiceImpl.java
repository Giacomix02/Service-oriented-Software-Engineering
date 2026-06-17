package it.univaq.sose.artistanalyzerprosumerrest.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
import it.univaq.sose.artistanalyzerprosumerrest.provider.MusicStatsDataProvider;


@Service
public class SongServiceImpl implements SongService {


    @Autowired
    private MusicStatsDataProvider songDataProvider;
    


    @Override
    @Transactional(readOnly=true)
    public List<Song> findAll() {
        return songDataProvider.getAllSongs();
    }

    @Override
    @Transactional(readOnly=true)
    public Song findById(int id) {
        return songDataProvider.getSongById(id);
    }

    @Override
    @Transactional(readOnly=true)
    public List<Song> findByName(String name) {

        List<Song> filteredSongs = new ArrayList<Song>();
        List<Song> songs = songDataProvider.getAllSongs();
        for (Song song : songs) {
            if (song.getName().contains(name)) {
                filteredSongs.add(song);
            }
        }
        return filteredSongs;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Song> findByArtistName(String name) {
        return songDataProvider.getAllByArtist(name);
    }
    

}
