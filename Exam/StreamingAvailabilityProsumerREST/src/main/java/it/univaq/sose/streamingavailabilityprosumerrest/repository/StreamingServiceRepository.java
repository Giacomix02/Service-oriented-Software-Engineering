package it.univaq.sose.streamingavailabilityprosumerrest.repository;

import it.univaq.sose.streamingavailabilityprosumerrest.model.StreamingService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StreamingServiceRepository extends JpaRepository<StreamingService, Integer> {
    Optional<StreamingService> findById(Integer id);
    Optional<StreamingService> findByName(String name);
    List<StreamingService> findBy();
}
