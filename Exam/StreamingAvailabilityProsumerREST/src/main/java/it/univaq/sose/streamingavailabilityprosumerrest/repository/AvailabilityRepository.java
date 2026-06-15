package it.univaq.sose.streamingavailabilityprosumerrest.repository;

import it.univaq.sose.streamingavailabilityprosumerrest.model.Availability;
import it.univaq.sose.streamingavailabilityprosumerrest.model.StreamingService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
    List<Availability> findAllBySong_Id(Integer songId);
    List<Availability> findAllByStreamingService_Id(Integer streamingServiceId);
}
