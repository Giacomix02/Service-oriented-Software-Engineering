package it.univaq.sose.streamingavailabilityprosumerrest.service;

import it.univaq.sose.streamingavailabilityprosumerrest.model.Song;
import it.univaq.sose.streamingavailabilityprosumerrest.model.StreamingService;

import java.util.List;

public interface AvailabilityService {
    List<StreamingService> getAviableServicesForSong(Integer songId);
    List<Song> getAviableSongsForService(Integer serviceId);
}
