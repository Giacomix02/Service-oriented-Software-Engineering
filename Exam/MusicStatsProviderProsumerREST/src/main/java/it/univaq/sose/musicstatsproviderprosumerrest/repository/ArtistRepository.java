package it.univaq.sose.musicstatsproviderprosumerrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.univaq.sose.musicstatsproviderprosumerrest.model.Artist;


@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    Artist findByName(String name);

    
} 
