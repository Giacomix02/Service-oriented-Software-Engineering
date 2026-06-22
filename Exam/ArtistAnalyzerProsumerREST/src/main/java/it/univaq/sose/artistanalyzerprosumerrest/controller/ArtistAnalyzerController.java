package it.univaq.sose.artistanalyzerprosumerrest.controller;

import java.util.List;

import it.univaq.sose.artistanalyzerprosumerrest.dto.ArtistDTO;
import it.univaq.sose.artistanalyzerprosumerrest.dto.SongDTO;
import it.univaq.sose.artistanalyzerprosumerrest.dto.StreamingServiceDTO;
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

import it.univaq.sose.artistanalyzerprosumerrest.model.Artist;
import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
import it.univaq.sose.artistanalyzerprosumerrest.service.ArtistService;
import it.univaq.sose.artistanalyzerprosumerrest.service.SongService;

@RestController
@RequestMapping("/artist-analyzer")
public class ArtistAnalyzerController {
	
    @Autowired
	private final SongService songService;

	@Autowired
	private final ArtistService artistService;

	@Autowired
	private final StreamingServiceService streamingServiceService;

    @Value("${server.port}")
	private String portNumber;

    public ArtistAnalyzerController(SongService songService, ArtistService artistService, StreamingServiceService streamingServiceService) {
        this.songService = songService;
		this.artistService = artistService;
        this.streamingServiceService = streamingServiceService;
    }

	@GetMapping("artists")
	public ResponseEntity<List<ArtistDTO>> getAllArtists() {
		return new ResponseEntity<List<ArtistDTO>>(artistService.findAll(), HttpStatus.OK);
	}

	@GetMapping("artists/{id}")
	public ResponseEntity<ArtistDTO> getArtistById(@PathVariable int id) {
		return new ResponseEntity<ArtistDTO>(artistService.findById(id), HttpStatus.OK);
	}

	// Get all songs
	@GetMapping("songs")
	public ResponseEntity<List<SongDTO>> getAllSongs() {
		System.out.println(portNumber);
		return new ResponseEntity<List<SongDTO>>(songService.findAll(), HttpStatus.OK);
	}

	// Get a song by is id
	@GetMapping("songs/{id}")
	public ResponseEntity<SongDTO> getSongById(@PathVariable("id") Integer id) {
		return new ResponseEntity<SongDTO>(songService.findById(id), HttpStatus.OK);
	}

	// Get a single song by the name
	@GetMapping("songs/by-name/{name}")
	public ResponseEntity<List<SongDTO>> getSongByName(@PathVariable String name) { // not inserting ("name") is the same that inserting it, it can be retrieved by the String
		return new ResponseEntity<List<SongDTO>>(songService.findByName(name), HttpStatus.OK);
	}

	// Get all songs by artist name
	@GetMapping("songs/by-artist/{name}")
	public ResponseEntity<List<SongDTO>> getSongsByArtistName(@PathVariable("name") String name) {
		return new ResponseEntity<List<SongDTO>>(songService.findByArtistName(name), HttpStatus.OK);
	}

	//Get all available streaming services
	@GetMapping("streaming-services")
	public ResponseEntity<List<StreamingServiceDTO>> getStreamingServices() {
		return new ResponseEntity<List<StreamingServiceDTO>>(streamingServiceService.getAll(), HttpStatus.OK);
	}

}
