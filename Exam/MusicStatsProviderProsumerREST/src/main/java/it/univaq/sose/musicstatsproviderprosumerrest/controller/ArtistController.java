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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.univaq.sose.musicstatsproviderprosumerrest.model.Artist;
import it.univaq.sose.musicstatsproviderprosumerrest.service.ArtistService;

@RestController
@Tag(name = "Artist related endpoints")
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
    @Operation(summary = "Get all artists")
    @ApiResponse(responseCode = "200", description = "All artists are returned")
    public ResponseEntity<List<ArtistDTO>> getAll() {
        List<ArtistDTO> artistsDTO = artistMapper.artistsToArtistsDTO(artistService.findAll());
        return new ResponseEntity<>(artistsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get artist by id")
    @ApiResponse(responseCode = "200", description = "artist is found and returned")
    @ApiResponse(responseCode = "404", description = "artist not found")
    public ResponseEntity<ArtistDTO> getById(@PathVariable("id") Integer id){
        ArtistDTO artistDTO = artistMapper.artistToArtistDTO(artistService.findById(id));
        return new ResponseEntity<>(artistDTO, HttpStatus.OK);
    }

    @GetMapping("/by-name/{name}")
    @Operation(summary = "Get artist by name")
    @ApiResponse(responseCode = "200", description = "artist is found and returned")
    @ApiResponse(responseCode = "404", description = "artist not found")
    public ResponseEntity<ArtistDTO> getById(@PathVariable("name") String name){
        ArtistDTO artistDTO = artistMapper.artistToArtistDTO(artistService.findByName(name));
        return new ResponseEntity<>(artistDTO, HttpStatus.OK);
    }

}
