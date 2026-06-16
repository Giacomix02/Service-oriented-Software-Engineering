package it.univaq.sose.musicstatsproviderprosumerrest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.sose.musicstatsproviderprosumerrest.model.Song;


@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    Song findByName(String name);
    List<Song> findByArtistName(String name);
    
} 
