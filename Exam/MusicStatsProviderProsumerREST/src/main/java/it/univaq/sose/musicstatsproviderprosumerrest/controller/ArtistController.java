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

import it.univaq.sose.musicstatsproviderprosumerrest.model.Artist;
import it.univaq.sose.musicstatsproviderprosumerrest.service.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    
    @Autowired
    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAll() {
        return new ResponseEntity<>(artistService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getById(@PathVariable("id") Integer id){
        return new ResponseEntity<>(artistService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<Artist> getById(@PathVariable("name") String name){
        return new ResponseEntity<>(artistService.findByName(name), HttpStatus.OK);
    }



    @Value("${server.port}")
	private String portNumber;


}
