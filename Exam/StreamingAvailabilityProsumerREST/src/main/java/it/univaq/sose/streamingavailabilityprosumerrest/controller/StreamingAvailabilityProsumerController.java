package it.univaq.sose.streamingavailabilityprosumerrest.controller;

import it.univaq.sose.streamingavailabilityprosumerrest.model.Song;
import it.univaq.sose.streamingavailabilityprosumerrest.model.StreamingService;
import it.univaq.sose.streamingavailabilityprosumerrest.service.AvailabilityService;
import it.univaq.sose.streamingavailabilityprosumerrest.service.StreamingServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/streamingAvailability")
public class StreamingAvailabilityProsumerController {

    @Autowired
    private AvailabilityService availabilityService;

 
    @Autowired
    private StreamingServiceService streamingServiceService;

    @Value("${server.port}")
    private String portNumber;

    @GetMapping("getSongAvailability/{Song_ID}")
    public ResponseEntity<List<StreamingService>> getSongAvailability(@PathVariable Integer Song_ID) {
        System.out.println("--------- getSongAvailability requested ---------");
        return ResponseEntity.ok(availabilityService.getAviableServicesForSong(Song_ID));
    }

    @GetMapping("getAllSongsForService/{Service_ID}")
    public ResponseEntity<List<Song>> getAllSongsForService(@PathVariable Integer Service_ID) {
        System.out.println("--------- getAllSongsForService requested ---------");
        return ResponseEntity.ok(availabilityService.getAviableSongsForService(Service_ID));
    }

    @GetMapping("getAllStreamingServices")
    public ResponseEntity<List<StreamingService>> getAllStreamingServices() {
        System.out.println("--------- getAllStreamingServices requested ---------");
        return ResponseEntity.ok(streamingServiceService.getAll());
    }

}
