package it.univaq.sose.ticketresellerproviderrest.repository;

import it.univaq.sose.ticketresellerproviderrest.model.Ticket;
import java.util.List;

public interface TicketRepository {
    List<Ticket> getTicketsByEvent(int eventGlobalId);

    Ticket getTicket(int eventGlobalId, int ticketId);
}