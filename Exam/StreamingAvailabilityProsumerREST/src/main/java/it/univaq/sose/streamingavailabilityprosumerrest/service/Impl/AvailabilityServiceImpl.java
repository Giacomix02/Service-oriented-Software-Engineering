package it.univaq.sose.streamingavailabilityprosumerrest.service.Impl;

import it.univaq.sose.streamingavailabilityprosumerrest.model.Availability;
import it.univaq.sose.streamingavailabilityprosumerrest.model.Song;
import it.univaq.sose.streamingavailabilityprosumerrest.model.StreamingService;
import it.univaq.sose.streamingavailabilityprosumerrest.repository.AvailabilityRepository;
import it.univaq.sose.streamingavailabilityprosumerrest.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService{


    @Autowired
    private AvailabilityRepository repository;


    @Override
    public List<StreamingService> getAviableServicesForSong(Integer songId) {
        List<StreamingService> streamingServices = new ArrayList<>();
        List<Availability> availabilities = repository.findAllBySong_Id(songId);
        availabilities.forEach(availability -> streamingServices.add(availability.getStreamingService()));
        return streamingServices;
    }

    @Override
    public List<Song> getAviableSongsForService(Integer serviceId) {
        List<Song> songs = new ArrayList<>();
        List<Availability> availabilities = repository.findAllByStreamingService_Id(serviceId);
        availabilities.forEach(availability -> songs.add(availability.getSong()));
        return songs;
    }
}
