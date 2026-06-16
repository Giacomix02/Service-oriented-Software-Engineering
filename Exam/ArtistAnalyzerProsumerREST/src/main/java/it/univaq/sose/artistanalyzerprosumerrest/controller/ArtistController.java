// package it.univaq.sose.artistanalyzerprosumerrest.controller;
//TODO
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import it.univaq.sose.artistanalyzerprosumerrest.model.Song;
// import it.univaq.sose.artistanalyzerprosumerrest.service.SongService;

// @RestController
// @RequestMapping("/artist")
// public class ArtistController {
//     @Autowired
// 	private final ArtistService artistService;

//     @Value("${server.port}")
// 	private String portNumber;

//     public SongController(SongService songService) {
//         this.songService = songService;
//     }

// 	@GetMapping
// 	public ResponseEntity<List<Song>> getAllArtists() {
// 		System.out.println(portNumber);
// 		return new ResponseEntity<List<Song>>(artistService.findAll(), HttpStatus.OK);
// 	}

// 	@GetMapping("/{id}")
// 	public ResponseEntity<Song> getArtistById(@PathVariable("id") Integer id) {
// 		System.out.println(portNumber);
// 		return new ResponseEntity<Song>(artistService.findById(id), HttpStatus.OK);
// 	}

// 	@GetMapping("/name/{name}")
// 	public ResponseEntity<Song> getArtistByName(@PathVariable("name") String name) {
// 		return new ResponseEntity<Song>(artistService.findByName(name), HttpStatus.OK);
// 	}
// }
