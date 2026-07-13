package it.univaq.sose.ticketresellerproviderrest.service;

import it.univaq.sose.ticketresellerproviderrest.model.Ticket;
import it.univaq.sose.ticketresellerproviderrest.repository.EventRepository;
import it.univaq.sose.ticketresellerproviderrest.repository.EventRepositoryImpl;
import it.univaq.sose.ticketresellerproviderrest.repository.TicketRepository;
import it.univaq.sose.ticketresellerproviderrest.repository.TicketRepositoryImpl;

import java.util.List;

public class TicketService {
    private final TicketRepository ticketRepository = new TicketRepositoryImpl();
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public List<Ticket> getEventTickets(int eventGlobalId) {
        if (!eventRepository.existsByGlobalId(eventGlobalId)) {
            return null;
        }

        return ticketRepository.getTicketsByEvent(eventGlobalId);
    }

    public Ticket getTicket(int eventGlobalId, int ticketId) {
        return ticketRepository.getTicket(eventGlobalId, ticketId);
    }
}