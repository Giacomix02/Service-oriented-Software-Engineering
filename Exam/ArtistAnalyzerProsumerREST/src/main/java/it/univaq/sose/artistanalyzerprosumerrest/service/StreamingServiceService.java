package it.univaq.sose.artistanalyzerprosumerrest.service;

import it.univaq.sose.artistanalyzerprosumerrest.dto.StreamingServiceDTO;
import it.univaq.sose.artistanalyzerprosumerrest.model.StreamingService;

import java.util.List;

public interface StreamingServiceService {
    List<StreamingServiceDTO> getAll();
}
