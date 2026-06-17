package it.univaq.sose.artistanalyzerprosumerrest.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.sose.artistanalyzerprosumerrest.model.Artist;
import it.univaq.sose.artistanalyzerprosumerrest.provider.MusicStatsDataProvider;

@Service
public class ArtistServiceImpl implements ArtistService {

    // @Autowired
    // private SongRepository songRepository;
    

    @Autowired
    private MusicStatsDataProvider musicStatsDataProvider;

    @Override
    public List<Artist> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Artist findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Artist findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }

    // @Override
    // @Transactional(readOnly=true)
    // public Job findById(Long id) {
    // 	return repository.findById(id).orElse(null);
    // }

    // @Override
    // @Transactional
    // public List<Song> findAll() {
    //     return songRepository.findAll();
    // }

    // @Override
    // @Transactional(readOnly=true)
    // public Song findById(int id) {
    //    return songRepository.getReferenceById(id);
    // }

    // @Override
    // public Song findByName(String name) {
    //     Song song = new Song();
    //     song.setName(name);
    //     return songRepository.findByName(name);
    // }


}