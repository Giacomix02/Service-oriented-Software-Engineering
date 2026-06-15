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
    public Song findById(int id) {
       return songRepository.getReferenceById(id);
    }
    
}
