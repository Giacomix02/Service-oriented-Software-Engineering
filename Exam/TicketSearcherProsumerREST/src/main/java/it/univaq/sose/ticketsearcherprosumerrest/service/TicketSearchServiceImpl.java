package it.univaq.sose.ticketsearcherprosumerrest.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.sose.ticketsearcherprosumerrest.dto.EventDTO;
import it.univaq.sose.ticketsearcherprosumerrest.dto.TicketComparisonDTO;
import it.univaq.sose.ticketsearcherprosumerrest.dto.TicketOfferDTO;
import it.univaq.sose.ticketsearcherprosumerrest.provider.BoxOfficeSoapProvider;
import it.univaq.sose.ticketsearcherprosumerrest.provider.TicketResellerDataProvider;
import reactor.core.publisher.Mono;

@Service
public class TicketSearchServiceImpl implements TicketSearchService {

    @Autowired
    private BoxOfficeSoapProvider boxOfficeSoapProvider;

    @Autowired
    private TicketResellerDataProvider ticketResellerDataProvider;

    @Override
    public TicketComparisonDTO compareTickets(int eventGlobalId) {

        Mono<List<TicketOfferDTO>> officialMono = boxOfficeSoapProvider.getOfficialTickets(eventGlobalId);
        Mono<List<TicketOfferDTO>> resaleMono = ticketResellerDataProvider.getResaleTickets(eventGlobalId);

        return Mono.zip(officialMono, resaleMono)
                .map(tuple -> {
                    List<TicketOfferDTO> official = tuple.getT1();
                    List<TicketOfferDTO> resale = tuple.getT2();

                    TicketOfferDTO cheapest = java.util.stream.Stream.concat(official.stream(), resale.stream())
                            .min(Comparator.comparingDouble(TicketOfferDTO::getPrice))
                            .orElse(null);

                    return new TicketComparisonDTO(eventGlobalId, official, resale, cheapest);
                })
                .block();
    }

    @Override
    public List<EventDTO> searchEvents(String name) {
        return boxOfficeSoapProvider.searchEventsByName(name).block();
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return boxOfficeSoapProvider.getAllEvents().block();
    }

    public Mono<TicketComparisonDTO> searchTicketsByCriteria(String eventName, String resellerType) {
        return boxOfficeSoapProvider.searchEventsByName(eventName)
                .flatMap(events -> {
                    if (events == null || events.isEmpty()) {
                        return Mono.empty();
                    }

                    int globalId = events.get(0).getEventGlobalId();

                    Mono<List<TicketOfferDTO>> officialMono = Mono.just(new ArrayList<>());
                    Mono<List<TicketOfferDTO>> resaleMono = Mono.just(new ArrayList<>());

                    if (resellerType == null || resellerType.equalsIgnoreCase("OFFICIAL") || resellerType.equalsIgnoreCase("BOTH")) {
                        officialMono = boxOfficeSoapProvider.getOfficialTickets(globalId).onErrorReturn(new ArrayList<>());
                    }

                    if (resellerType == null || resellerType.equalsIgnoreCase("RESELLER") || resellerType.equalsIgnoreCase("BOTH")) {
                        resaleMono = ticketResellerDataProvider.getResaleTickets(globalId).onErrorReturn(new ArrayList<>());
                    }

                    return Mono.zip(officialMono, resaleMono).map(tuple -> {
                        List<TicketOfferDTO> officials = tuple.getT1();
                        List<TicketOfferDTO> resales = tuple.getT2();

                        List<TicketOfferDTO> allTickets = new ArrayList<>();
                        allTickets.addAll(officials);
                        allTickets.addAll(resales);

                        TicketOfferDTO cheapest = allTickets.stream()
                                .min(Comparator.comparingDouble(TicketOfferDTO::getPrice))
                                .orElse(null);

                        return new TicketComparisonDTO(globalId, officials, resales, cheapest);
                    });
                });
    }
}