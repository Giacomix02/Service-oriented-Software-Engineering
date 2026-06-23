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

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.univaq.sose.artistanalyzerprosumerrest.model.Artist;
import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
import it.univaq.sose.artistanalyzerprosumerrest.service.ArtistService;
import it.univaq.sose.artistanalyzerprosumerrest.service.SongService;

@Tag(name = "All endpoints for the Artist Analyzer microservice")
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

	@Tag(name = "Artist related endpoints")
	@Operation(summary = "Return all artists")
	@ApiResponse(responseCode = "200", description = "All artists are returned")
//   @ApiResponse(responseCode = "400", description = "Invalid id supplied", 
//     content = @Content)
//   @ApiResponse(responseCode = "404", description = "Book not found", 
//     content = @Content) 
	@GetMapping("artists")
	public ResponseEntity<List<ArtistDTO>> getAllArtists() {
		return new ResponseEntity<List<ArtistDTO>>(artistService.findAll(), HttpStatus.OK);
	}

	@Tag(name = "Artist related endpoints")
	@GetMapping("artists/{id}")
	@Operation(summary = "Return an artist by id" )
	@ApiResponse(responseCode = "200", description = "Artist is found and returned")
  	@ApiResponse(responseCode = "404", description = "Artist not found", content = @Content) 
	public ResponseEntity<ArtistDTO> getArtistById(@PathVariable int id) {
		return new ResponseEntity<ArtistDTO>(artistService.findById(id), HttpStatus.OK);
	}

	@Tag(name = "Song related endpoints")
	// Get all songs
	@GetMapping("songs")
	@Operation(summary = "Return all songs" )
	@ApiResponse(responseCode = "200", description = "All songs are returned")
  	// @ApiResponse(responseCode = "404", description = "Artist not found", content = @Content)
	public ResponseEntity<List<SongDTO>> getAllSongs() {
		System.out.println(portNumber);
		return new ResponseEntity<List<SongDTO>>(songService.findAll(), HttpStatus.OK);
	}

	@Tag(name = "Song related endpoints")
	// Get a song by is id
	@GetMapping("songs/{id}")
	@Operation(summary = "Return a song by id" )
	@ApiResponse(responseCode = "200", description = "Song is found and returned")
  	@ApiResponse(responseCode = "404", description = "Song not found", content = @Content) 
	public ResponseEntity<SongDTO> getSongById(@PathVariable("id") Integer id) {
		return new ResponseEntity<SongDTO>(songService.findById(id), HttpStatus.OK);
	}


	@Tag(name = "Song related endpoints")
	// Get a single song by the name
	@GetMapping("songs/by-name/{name}")
	@Operation(summary = "Return a song by name" )
	@ApiResponse(responseCode = "200", description = "Song is found and returned")
  	@ApiResponse(responseCode = "404", description = "Song not found", content = @Content) 
	public ResponseEntity<List<SongDTO>> getSongByName(@PathVariable String name) { // not inserting ("name") is the same that inserting it, it can be retrieved by the String
		return new ResponseEntity<List<SongDTO>>(songService.findByName(name), HttpStatus.OK);
	}

	@Tag(name = "Song related endpoints")
	// Get all songs by artist name
	@GetMapping("songs/by-artist/{name}")
	@Operation(summary = "Return songs belonging to an artist by its name" )
	@ApiResponse(responseCode = "200", description = "Artist is found and songs are returned")
  	@ApiResponse(responseCode = "404", description = "Artist not found", content = @Content) 
	public ResponseEntity<List<SongDTO>> getSongsByArtistName(@PathVariable("name") String name) {
		return new ResponseEntity<List<SongDTO>>(songService.findByArtistName(name), HttpStatus.OK);
	}

	//Get all available streaming services
	@GetMapping("streaming-services")
	@Operation(summary = "Get all available streaming services" )
  	// @ApiResponse(responseCode = "404", description = "Artist not found", content = @Content) 
	public ResponseEntity<List<StreamingServiceDTO>> getStreamingServices() {
		return new ResponseEntity<List<StreamingServiceDTO>>(streamingServiceService.getAll(), HttpStatus.OK);
	}

}
