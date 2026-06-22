package it.univaq.sose.streamingavailabilityproviderrest.service.Impl;

import it.univaq.sose.streamingavailabilityproviderrest.dto.AvailabilityDTO;
import it.univaq.sose.streamingavailabilityproviderrest.dto.Mappers.SongMapper;
import it.univaq.sose.streamingavailabilityproviderrest.dto.Mappers.StreamingServiceMapper;
import it.univaq.sose.streamingavailabilityproviderrest.dto.SongDTO;
import it.univaq.sose.streamingavailabilityproviderrest.dto.StreamingServiceDTO;
import it.univaq.sose.streamingavailabilityproviderrest.model.Availability;
import it.univaq.sose.streamingavailabilityproviderrest.model.Song;
import it.univaq.sose.streamingavailabilityproviderrest.model.StreamingService;
import it.univaq.sose.streamingavailabilityproviderrest.repository.AvailabilityRepository;
import it.univaq.sose.streamingavailabilityproviderrest.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AvailabilityServiceImpl implements AvailabilityService{


    @Autowired
    private AvailabilityRepository repository;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private StreamingServiceMapper streamingServiceMapper;


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

    @Override
    public List<AvailabilityDTO> getAllAvailabilities() {
        List<Availability> availabilities = repository.findAll();

        Map<Song, List<StreamingService>> songToServicesMap = availabilities.stream()
                .collect(Collectors.groupingBy(
                        Availability::getSong,
                        Collectors.mapping(Availability::getStreamingService, Collectors.toList())
                ));

        List<AvailabilityDTO> dtoList = new ArrayList<>();
        int counter = 1;

        for (Map.Entry<Song, List<StreamingService>> entry : songToServicesMap.entrySet()) {
            Song song = entry.getKey();
            List<StreamingService> services = entry.getValue();
            Availability firstAvailability = availabilities.stream()
                    .filter(availability -> availability.getSong().getId().equals(song.getId()))
                    .findFirst()
                    .orElse(null);

            SongDTO songDTO = songMapper.songToSongDTO(song);

            List<StreamingServiceDTO> serviceDTOs = streamingServiceMapper.streamingServicesToStreamingServicesDTO(services);

            AvailabilityDTO dto = new AvailabilityDTO(counter++, songDTO, serviceDTOs);

            if (firstAvailability != null) {
                dto.setCreatedAt(firstAvailability.getCreatedAt());
                dto.setUpdatedAt(firstAvailability.getUpdatedAt());
            }

            dtoList.add(dto);
        }

        return dtoList;
    }
}
