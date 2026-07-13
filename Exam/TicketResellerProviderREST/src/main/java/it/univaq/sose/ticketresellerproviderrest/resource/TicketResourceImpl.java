package it.univaq.sose.ticketresellerproviderrest.resource;

import it.univaq.sose.ticketresellerproviderrest.model.Ticket;
import it.univaq.sose.ticketresellerproviderrest.service.TicketService;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class TicketResourceImpl implements TicketResource {

    private final TicketService ticketService = new TicketService();

    @Override
    public Response getEventTickets(int eventGlobalId) {
        List<Ticket> tickets = ticketService.getEventTickets(eventGlobalId);

        if (tickets == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Evento not found\"}")
                    .build();
        }

        return Response.ok(tickets).build();
    }

    @Override
    public Response getTicketGet(int eventGlobalId, int ticketId) {
        Ticket ticket = ticketService.getTicket(eventGlobalId, ticketId);

        if (ticket == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Ticket not found for this event\"}")
                    .build();
        }
        return Response.ok(ticket).build();
    }
}