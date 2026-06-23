package it.univaq.sose.musicstatsproviderprosumerrest.controller;
import java.util.List;

import it.univaq.sose.musicstatsproviderprosumerrest.dto.Mappers.SongMapper;
import it.univaq.sose.musicstatsproviderprosumerrest.dto.SongDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.univaq.sose.musicstatsproviderprosumerrest.model.Song;
import it.univaq.sose.musicstatsproviderprosumerrest.service.SongService;

@RestController
@RequestMapping("/songs")
public class SongController {
    
	@Autowired
	private final SongService songService;

	@Autowired
	private SongMapper songMapper;

	@Value("${server.port}")
	private String portNumber;

    public SongController(SongService songService) {
        this.songService = songService;
    }

	@GetMapping
	@Operation(summary = "Get all songs")
    @ApiResponse(responseCode = "200", description = "all songs are returned")
    // @ApiResponse(responseCode = "404", description = "song not found")
	public ResponseEntity<List<SongDTO>> getAllSongs() {
		System.out.println("---- Give all Songs requested ----");
		List<SongDTO> songDTOList = songMapper.songsToSongsDTO(songService.findAll());
		return new ResponseEntity<>(songDTOList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get song by id")
    @ApiResponse(responseCode = "200", description = "song is found and returned")
    @ApiResponse(responseCode = "404", description = "song not found")
	public ResponseEntity<SongDTO> getSongById(@PathVariable("id") Integer id) {
		System.out.println("---- Search Song by id requested ----");
		SongDTO songDTOList = songMapper.songToSongDTO(songService.findById(id));
		return new ResponseEntity<>(songDTOList, HttpStatus.OK);
	}

	@GetMapping("/by-name/{name}")
	@Operation(summary = "Get song by name")
    @ApiResponse(responseCode = "200", description = "song is found and returned")
    @ApiResponse(responseCode = "404", description = "song not found")
	public ResponseEntity<SongDTO> getSongByName(@PathVariable("name") String name) {
		System.out.println("---- Search Song by name requested ----");
		SongDTO songDTOList = songMapper.songToSongDTO(songService.findByName(name));
		return new ResponseEntity<>(songDTOList, HttpStatus.OK);
	}

	@GetMapping("/by-artist/{name}")
	@Operation(summary = "Get songs by their artist")
    @ApiResponse(responseCode = "200", description = "artist is found and songs are returned")
    @ApiResponse(responseCode = "404", description = "artist not found")
	public ResponseEntity<List<SongDTO>> getSongsByArtist(@PathVariable("name") String name) {
		System.out.println("---- Search Songs by artist name requested ----");
		List<SongDTO> songsDTOList = songMapper.songsToSongsDTO(songService.findByArtistName(name));
		return new ResponseEntity<>(songsDTOList, HttpStatus.OK);
	}
}