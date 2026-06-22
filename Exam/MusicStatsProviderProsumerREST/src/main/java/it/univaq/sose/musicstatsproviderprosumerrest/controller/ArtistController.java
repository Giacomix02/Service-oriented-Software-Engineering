package it.univaq.sose.musicstatsproviderprosumerrest.controller;

import java.util.List;

import it.univaq.sose.musicstatsproviderprosumerrest.dto.ArtistDTO;
import it.univaq.sose.musicstatsproviderprosumerrest.dto.Mappers.ArtistMapper;
import it.univaq.sose.musicstatsproviderprosumerrest.dto.Mappers.SongMapper;
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

    @Value("${server.port}")
    private String portNumber;

    @Autowired
    private final ArtistService artistService;

    @Autowired
    private ArtistMapper artistMapper;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAll() {
        List<ArtistDTO> artistsDTO = artistMapper.artistsToArtistsDTO(artistService.findAll());
        return new ResponseEntity<>(artistsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getById(@PathVariable("id") Integer id){
        ArtistDTO artistDTO = artistMapper.artistToArtistDTO(artistService.findById(id));
        return new ResponseEntity<>(artistDTO, HttpStatus.OK);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<ArtistDTO> getById(@PathVariable("name") String name){
        ArtistDTO artistDTO = artistMapper.artistToArtistDTO(artistService.findByName(name));
        return new ResponseEntity<>(artistDTO, HttpStatus.OK);
    }

}
