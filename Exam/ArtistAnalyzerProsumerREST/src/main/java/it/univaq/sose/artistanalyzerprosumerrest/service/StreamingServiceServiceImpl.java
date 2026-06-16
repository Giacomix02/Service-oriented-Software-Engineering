package it.univaq.sose.artistanalyzerprosumerrest.service;

import it.univaq.sose.artistanalyzerprosumerrest.model.StreamingService;
import it.univaq.sose.artistanalyzerprosumerrest.provider.StreamingServiceDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StreamingServiceServiceImpl implements StreamingServiceService {

    @Autowired
    private StreamingServiceDataProvider streamingServiceDataProvider;

    @Override
    @Transactional(readOnly = true)
    public List<StreamingService> getAll(){
        return streamingServiceDataProvider.getAllStreamingServices();
    }
}
