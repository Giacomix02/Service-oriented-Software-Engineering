package it.univaq.sose.artistanalyzerprosumerrest.controller;

import java.util.List;

import it.univaq.sose.artistanalyzerprosumerrest.model.StreamingService;
import it.univaq.sose.artistanalyzerprosumerrest.service.StreamingServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
import it.univaq.sose.artistanalyzerprosumerrest.service.SongService;

@RestController
@RequestMapping("/artistAnalyzer")
public class ArtistAnalyzerController {
	
    @Autowired
	private final SongService songService;

	@Autowired
	private final StreamingServiceService streamingServiceService;

    @Value("${server.port}")
	private String portNumber;

    public ArtistAnalyzerController(SongService songService, StreamingServiceService streamingServiceService) {
        this.songService = songService;
        this.streamingServiceService = streamingServiceService;
    }

	// Get all songs
	@GetMapping
	public ResponseEntity<List<Song>> getAll() {
		System.out.println(portNumber);
		return new ResponseEntity<List<Song>>(songService.findAll(), HttpStatus.OK);
	}

	// Get a song by is id
	@GetMapping("song/{id}")
	public ResponseEntity<Song> getSongById(@PathVariable("id") Integer id) {
		return new ResponseEntity<Song>(songService.findById(id), HttpStatus.OK);
	}

	// Get a single song by the name
	@GetMapping("song/byname/{name}")
	public ResponseEntity<List<Song>> getSongByName(@PathVariable String name) { // not inserting ("name") is the same that inserting it, it can be retrieved by the String
		return new ResponseEntity<List<Song>>(songService.findByName(name), HttpStatus.OK);
	}

	// Get all songs by artist name
	@GetMapping("song/byartist/{name}")
	public ResponseEntity<List<Song>> getSongsByArtistName(@PathVariable("name") String name) {
		return new ResponseEntity<List<Song>>(songService.findByArtistName(name), HttpStatus.OK);
	}

	//Get all available streaming services
	@GetMapping("streamingServices")
	public ResponseEntity<List<StreamingService>> getStreamingServices() {
		return new ResponseEntity<List<StreamingService>>(streamingServiceService.getAll(), HttpStatus.OK);
	}

}
