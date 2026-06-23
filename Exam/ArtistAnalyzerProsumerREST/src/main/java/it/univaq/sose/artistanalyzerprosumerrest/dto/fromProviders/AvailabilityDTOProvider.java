package it.univaq.sose.artistanalyzerprosumerrest.dto.fromProviders;

import java.util.List;

import it.univaq.sose.artistanalyzerprosumerrest.dto.AuditDTO;
import it.univaq.sose.artistanalyzerprosumerrest.dto.SongDTO;
import it.univaq.sose.artistanalyzerprosumerrest.dto.StreamingServiceDTO;

public class AvailabilityDTOProvider extends AuditDTO{

    private SongDTOProvider song;

    public SongDTOProvider getSong() {
        return song;
    }

    public void setSong(SongDTOProvider song) {
        this.song = song;
    }

    private List<StreamingServiceDTO> streamingServices;
    
    

    public List<StreamingServiceDTO> getStreamingServices() {
        return streamingServices;
    }

    public void setStreamingServices(List<StreamingServiceDTO> streamingService) {
        this.streamingServices = streamingService;
    }

    
}