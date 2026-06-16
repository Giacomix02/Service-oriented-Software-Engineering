package it.univaq.sose.artistanalyzerprosumerrest.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
import it.univaq.sose.artistanalyzerprosumerrest.provider.SongDataProvider;


@Service
public class SongServiceImpl implements SongService {


    @Autowired
    private SongDataProvider songDataProvider;
    


	// @Override
	// @Transactional(readOnly=true)
	// public Job findById(Long id) {
	// 	return repository.findById(id).orElse(null);
	// }

    @Override
    @Transactional
    public List<Song> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByArtistByName'");
        // return songRepository.findAll();
    }

    @Override
    @Transactional(readOnly=true)
    public Song findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByArtistByName'");
    //    return songRepository.getReferenceById(id);
    }

    @Override
    public Song findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByArtistByName'");
        // Song song = new Song();
        // song.setName(name);
        // return songRepository.findByName(name);
    }

    @Override
    public List<Song> findByArtistName(String name) {
        return songDataProvider.getAll(name).toList();
    }
    

}
