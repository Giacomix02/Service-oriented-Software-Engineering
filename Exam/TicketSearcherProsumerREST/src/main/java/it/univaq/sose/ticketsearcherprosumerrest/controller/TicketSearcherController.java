package it.univaq.sose.ticketsearcherprosumerrest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import it.univaq.sose.ticketsearcherprosumerrest.dto.TicketSearchRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.univaq.sose.ticketsearcherprosumerrest.dto.EventDTO;
import it.univaq.sose.ticketsearcherprosumerrest.dto.TicketComparisonDTO;
import it.univaq.sose.ticketsearcherprosumerrest.dto.TicketOfferDTO;
import it.univaq.sose.ticketsearcherprosumerrest.service.TicketSearchService;

@Tag(name = "Ticket Searcher endpoints")
@RestController
@RequestMapping("/ticket-searcher")
public class TicketSearcherController {

    @Autowired
    private TicketSearchService ticketSearchService;

    @GetMapping("events/{eventGlobalId}/tickets")
    @Operation(summary = "Compares official tickets (SOAP) and reselled tickest (REST) for an event.")
    @ApiResponse(responseCode = "200", description = "Comparación devuelta correctamente")
    public ResponseEntity<TicketComparisonDTO> getTicketComparison(@PathVariable int eventGlobalId) {
        return new ResponseEntity<>(ticketSearchService.compareTickets(eventGlobalId), HttpStatus.OK);
    }

    @GetMapping("events/{eventGlobalId}/tickets/cheapest")
    @Operation(summary = "Returns the cheapest ticket between officials and reselled.")
    public ResponseEntity<TicketOfferDTO> getCheapestTicket(@PathVariable int eventGlobalId) {
        TicketComparisonDTO comparison = ticketSearchService.compareTickets(eventGlobalId);
        return new ResponseEntity<>(comparison.getCheapestOverall(), HttpStatus.OK);
    }

    @GetMapping("events")
    @Operation(summary = "Searches by name, or lists everyone if there is no 'name'.")
    public ResponseEntity<List<EventDTO>> searchEvents(@RequestParam(required = false) String name) {
        List<EventDTO> events = (name == null || name.isBlank())
                ? ticketSearchService.getAllEvents()
                : ticketSearchService.searchEvents(name);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PostMapping("/search")
    public Mono<TicketComparisonDTO> searchTickets(@RequestBody TicketSearchRequestDTO request) {
        return ticketSearchService.searchTicketsByCriteria(
                request.getEventName(),
                request.getResellerType()
        );
    }
}