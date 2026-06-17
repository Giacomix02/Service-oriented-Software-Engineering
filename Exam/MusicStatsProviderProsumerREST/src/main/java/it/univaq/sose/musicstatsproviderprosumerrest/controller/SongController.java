package it.univaq.sose.musicstatsproviderprosumerrest.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.sose.musicstatsproviderprosumerrest.model.Song;
import it.univaq.sose.musicstatsproviderprosumerrest.service.SongService;

@RestController
@RequestMapping("/song")
public class SongController {
    
	@Autowired
	private final SongService songService;

	@Value("${server.port}")
	private String portNumber;

    public SongController(SongService songService) {
        this.songService = songService;
    }

	@GetMapping
	public ResponseEntity<List<Song>> getAllSongs() {
		System.out.println("---- Give all Songs requested ----");
		return new ResponseEntity<List<Song>>(songService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Song> getSongById(@PathVariable("id") Integer id) {
		System.out.println("---- Search Song by id requested ----");
		return new ResponseEntity<Song>(songService.findById(id), HttpStatus.OK);
	}

	@GetMapping("/byName/{name}")
	public ResponseEntity<Song> getSongByName(@PathVariable("name") String name) {
		System.out.println("---- Search Song by name requested ----");
		return new ResponseEntity<Song>(songService.findByName(name), HttpStatus.OK);
	}

	@GetMapping("/byArtist/{name}")
	public ResponseEntity<List<Song>> getSongsByArtist(@PathVariable("name") String name) {
		System.out.println("---- Search Songs by artist name requested ----");
		return new ResponseEntity<List<Song>>(songService.findByArtistName(name), HttpStatus.OK);
	}
}