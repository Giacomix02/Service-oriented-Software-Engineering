package it.univaq.sose.streamingavailabilityproviderrest.dto.Mappers;

import it.univaq.sose.streamingavailabilityproviderrest.dto.SongDTO;
import it.univaq.sose.streamingavailabilityproviderrest.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongMapper {

    SongDTO songToSongDTO(Song song);

    List<SongDTO> songsToSongsDTO(List<Song> songs);
}
