package it.univaq.sose.ticketsearcherprosumerrest.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import it.univaq.sose.ticketsearcherprosumerrest.dto.TicketOfferDTO;
import it.univaq.sose.ticketsearcherprosumerrest.dto.fromProviders.TicketResellerOfferDTOProvider;
import reactor.core.publisher.Mono;

@Component
public class TicketResellerDataProvider {

    @Value("${ticketreseller.url}")
    private String resellerBaseUrl;

    private final WebClient webClient;

    public TicketResellerDataProvider(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<List<TicketOfferDTO>> getResaleTickets(int eventGlobalId) {
        return webClient.get()
                .uri(resellerBaseUrl + "/api/getEventTickets/{id}", eventGlobalId)
                .retrieve()
                .bodyToFlux(TicketResellerOfferDTOProvider.class)
                .map(this::toOfferDTO)
                .collectList();
    }

    private TicketOfferDTO toOfferDTO(TicketResellerOfferDTOProvider p) {
        return new TicketOfferDTO(
                p.getId(),
                p.getPrice(),
                p.getSeat(),
                p.getEventId(),
                TicketOfferDTO.Source.RESELLER
        );
    }
}