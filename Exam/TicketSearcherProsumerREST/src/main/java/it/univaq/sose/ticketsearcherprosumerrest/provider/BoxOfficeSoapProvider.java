package it.univaq.sose.ticketsearcherprosumerrest.provider;

import java.util.List;

import org.springframework.stereotype.Component;

import it.univaq.sose.ticketsearcherprosumerrest.dto.EventDTO;
import it.univaq.sose.ticketsearcherprosumerrest.dto.TicketOfferDTO;
import it.univaq.sose.ticketsearcherprosumerrest.soap.boxoffice.BoxOfficeService;
import it.univaq.sose.ticketsearcherprosumerrest.soap.boxoffice.Event;
import it.univaq.sose.ticketsearcherprosumerrest.soap.boxoffice.Ticket;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class BoxOfficeSoapProvider {

    private final BoxOfficeService boxOfficeService;

    public BoxOfficeSoapProvider(BoxOfficeService boxOfficeService) {
        this.boxOfficeService = boxOfficeService;
    }

    public Mono<List<TicketOfferDTO>> getOfficialTickets(int eventGlobalId) {
        return Mono.fromCallable(() -> boxOfficeService.getEventTickets(eventGlobalId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(tickets -> tickets.stream()
                        .map(this::toOfferDTO)
                        .toList());
    }

    public Mono<List<EventDTO>> searchEventsByName(String name) {
        return Mono.fromCallable(() -> boxOfficeService.searchByName(name))
                .subscribeOn(Schedulers.boundedElastic())
                .map(events -> events.stream()
                        .map(this::toEventDTO)
                        .toList());
    }

    public Mono<List<EventDTO>> getAllEvents() {
        return Mono.fromCallable(boxOfficeService::getAllEvents)
                .subscribeOn(Schedulers.boundedElastic())
                .map(events -> events.stream()
                        .map(this::toEventDTO)
                        .toList());
    }

    private TicketOfferDTO toOfferDTO(Ticket t) {
        return new TicketOfferDTO(
                t.getId(),
                t.getPrice(),
                t.getSeat(),
                t.getEventId(),
                TicketOfferDTO.Source.OFFICIAL
        );
    }

    private EventDTO toEventDTO(Event e) {
        return new EventDTO(
                e.getId(),
                e.getEventGlobalId(),
                e.getName(),
                e.getArtistName(),
                e.getLocation(),
                e.getDescription()
        );
    }
}