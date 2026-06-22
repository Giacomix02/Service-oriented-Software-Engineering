package it.univaq.sose.musicstatsproviderprosumerrest.dto.Mappers;

import it.univaq.sose.musicstatsproviderprosumerrest.dto.SongDTO;
import it.univaq.sose.musicstatsproviderprosumerrest.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongMapper {
        SongDTO songToSongDTO(Song song);

        List<SongDTO> songsToSongsDTO(List<Song> songs);
}
