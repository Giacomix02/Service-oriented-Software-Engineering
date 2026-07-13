package it.univaq.sose.ticketsearcherprosumerrest.service;

import java.util.List;

import it.univaq.sose.ticketsearcherprosumerrest.dto.EventDTO;
import it.univaq.sose.ticketsearcherprosumerrest.dto.TicketComparisonDTO;
import reactor.core.publisher.Mono;

public interface TicketSearchService {
    TicketComparisonDTO compareTickets(int eventGlobalId);
    List<EventDTO> searchEvents(String name);
    List<EventDTO> getAllEvents();
    Mono<TicketComparisonDTO> searchTicketsByCriteria(String eventName, String resellerType);
}