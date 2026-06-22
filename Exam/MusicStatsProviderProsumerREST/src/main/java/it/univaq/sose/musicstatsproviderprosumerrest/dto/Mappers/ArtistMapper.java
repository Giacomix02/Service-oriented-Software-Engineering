package it.univaq.sose.musicstatsproviderprosumerrest.dto.Mappers;

import it.univaq.sose.musicstatsproviderprosumerrest.dto.ArtistDTO;
import it.univaq.sose.musicstatsproviderprosumerrest.model.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistMapper {
    ArtistDTO artistToArtistDTO(Artist artist);

    List<ArtistDTO> artistsToArtistsDTO(List<Artist> artists);
}
