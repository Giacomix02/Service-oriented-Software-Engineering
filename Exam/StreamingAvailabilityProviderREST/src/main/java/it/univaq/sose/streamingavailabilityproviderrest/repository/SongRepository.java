package it.univaq.sose.streamingavailabilityproviderrest.repository;

import it.univaq.sose.streamingavailabilityproviderrest.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Integer> {
    @Override
    Optional<Song> findById(Integer id);

    @Override
    List<Song> findAll();
}
