package it.univaq.sose.streamingavailabilityproviderrest.service;

import it.univaq.sose.streamingavailabilityproviderrest.dto.AvailabilityDTO;
import it.univaq.sose.streamingavailabilityproviderrest.model.Availability;
import it.univaq.sose.streamingavailabilityproviderrest.model.Song;
import it.univaq.sose.streamingavailabilityproviderrest.model.StreamingService;

import java.util.List;

public interface AvailabilityService {
    List<StreamingService> getAviableServicesForSong(Integer songId);
    List<Song> getAviableSongsForService(Integer serviceId);
    List<AvailabilityDTO> getAllAvailabilities();
}
