package it.univaq.sose.streamingavailabilityproviderrest.dto.Mappers;

import it.univaq.sose.streamingavailabilityproviderrest.dto.AvailabilityDTO;
import it.univaq.sose.streamingavailabilityproviderrest.model.Availability;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AvailabilityMapper{
    AvailabilityDTO availabilityToAvailabilityDTO(Availability availability);

    List<AvailabilityDTO> availabilitiesToAvailabilitiesDTO(List<Availability> availabilities);
}
