package it.univaq.sose.streamingavailabilityproviderrest.dto.Mappers;

import it.univaq.sose.streamingavailabilityproviderrest.dto.StreamingServiceDTO;
import it.univaq.sose.streamingavailabilityproviderrest.model.StreamingService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StreamingServiceMapper {
    StreamingServiceDTO streamingServiceToStreamingServiceDTO(StreamingService streamingService);

    List<StreamingServiceDTO> streamingServicesToStreamingServicesDTO(List<StreamingService> streamingServices);
}
