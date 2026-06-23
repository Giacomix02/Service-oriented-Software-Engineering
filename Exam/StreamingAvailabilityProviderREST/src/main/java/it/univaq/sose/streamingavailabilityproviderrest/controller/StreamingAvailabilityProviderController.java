package it.univaq.sose.streamingavailabilityproviderrest.controller;

import it.univaq.sose.streamingavailabilityproviderrest.dto.AvailabilityDTO;
import it.univaq.sose.streamingavailabilityproviderrest.dto.Mappers.AvailabilityMapper;
import it.univaq.sose.streamingavailabilityproviderrest.dto.Mappers.SongMapper;
import it.univaq.sose.streamingavailabilityproviderrest.dto.Mappers.StreamingServiceMapper;
import it.univaq.sose.streamingavailabilityproviderrest.dto.SongDTO;
import it.univaq.sose.streamingavailabilityproviderrest.dto.StreamingServiceDTO;
import it.univaq.sose.streamingavailabilityproviderrest.model.Availability;
import it.univaq.sose.streamingavailabilityproviderrest.service.AvailabilityService;
import it.univaq.sose.streamingavailabilityproviderrest.service.StreamingServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/streaming-availability")
public class StreamingAvailabilityProviderController {

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private AvailabilityMapper availabilityMapper;

    @Autowired
    private SongMapper songMapper;

    @Autowired
    private StreamingServiceMapper streamingServiceMapper;


 
    @Autowired
    private StreamingServiceService streamingServiceService;

    @Value("${server.port}")
    private String portNumber;

    @GetMapping("get-song-availability/{Song_ID}")
    @Operation(summary = "Get availability of a song by its id")
    @ApiResponse(responseCode = "200", description = "song is found and availability is returned")
    @ApiResponse(responseCode = "404", description = "song not found")
    public ResponseEntity<List<StreamingServiceDTO>> getSongAvailability(@PathVariable Integer Song_ID) {
        System.out.println("--------- getSongAvailability requested ---------");
        List< StreamingServiceDTO> streamingServiceDTOList = streamingServiceMapper.streamingServicesToStreamingServicesDTO(availabilityService.getAviableServicesForSong(Song_ID));
        return ResponseEntity.ok(streamingServiceDTOList);
    }

    @GetMapping("get-all-songs-for-service/{Service_ID}")
    @Operation(summary = "Get all song ids belonging to a service by its id")
    @ApiResponse(responseCode = "200", description = "service is valid and songs are returned")
    @ApiResponse(responseCode = "404", description = "service not valid")
    public ResponseEntity<List<SongDTO>> getAllSongsForService(@PathVariable Integer Service_ID) {
        System.out.println("--------- getAllSongsForService requested ---------");
        List<SongDTO> songDTOList = songMapper.songsToSongsDTO(availabilityService.getAviableSongsForService(Service_ID));
        return ResponseEntity.ok(songDTOList);
    }

    @GetMapping("get-all-streaming-services")
    @Operation(summary = "Get all available streaming services")
    @ApiResponse(responseCode = "200", description = "Streaming services are returned")
    // @ApiResponse(responseCode = "404", description = "song not found")
    public ResponseEntity<List<StreamingServiceDTO>> getAllStreamingServices() {
        System.out.println("--------- getAllStreamingServices requested ---------");
        List<StreamingServiceDTO> streamingServiceDTOList = streamingServiceMapper.streamingServicesToStreamingServicesDTO(streamingServiceService.getAll());
        return ResponseEntity.ok(streamingServiceDTOList);
    }

    @GetMapping("get-all-availabilities")
    @Operation(summary = "Get all song ids with their list of streaming service ids on which it's available")
    @ApiResponse(responseCode = "200", description = "All availabilities are returned")
    // @ApiResponse(responseCode = "404", description = "song not found")
    public ResponseEntity<List<AvailabilityDTO>> getAllAvailabilities() {
        System.out.println("--------- getAllAvailabilities requested ---------");
        return ResponseEntity.ok(availabilityService.getAllAvailabilities());
    }

}
