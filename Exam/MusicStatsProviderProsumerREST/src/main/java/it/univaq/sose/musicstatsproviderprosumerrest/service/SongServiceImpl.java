package it.univaq.sose.musicstatsproviderprosumerrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.sose.musicstatsproviderprosumerrest.model.Song;
import it.univaq.sose.musicstatsproviderprosumerrest.repository.SongRepository;

@Service
public class SongServiceImpl implements SongService {


    @Autowired
    private SongRepository songRepository;
    


	// @Override
	// @Transactional(readOnly=true)
	// public Job findById(Long id) {
	// 	return repository.findById(id).orElse(null);
	// }

    @Override
    @Transactional
    public List<Song> findAll() {
        return songRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public Song findById(int id) {
       return songRepository.findById(id).orElse(null);
    }

    @Override
    public List<Song> findByName(String name) {
        return songRepository.findByNameContaining(name);
    }

    @Override
    public List<Song> findByArtistName(String name) {
        return songRepository.findByArtistName(name);
    }
    

}
