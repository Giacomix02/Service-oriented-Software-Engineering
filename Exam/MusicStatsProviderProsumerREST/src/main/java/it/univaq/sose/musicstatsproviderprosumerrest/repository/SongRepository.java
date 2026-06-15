package it.univaq.sose.musicstatsproviderprosumerrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.sose.musicstatsproviderprosumerrest.model.Song;


@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    
} 
