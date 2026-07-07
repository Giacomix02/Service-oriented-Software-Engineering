package it.univaq.sose.LegacyBoxOfficeProviderSOAP.service;

import it.univaq.sose.LegacyBoxOfficeProviderSOAP.BoxOfficeException;
import it.univaq.sose.LegacyBoxOfficeProviderSOAP.model.Event;
import it.univaq.sose.LegacyBoxOfficeProviderSOAP.model.Ticket;
import it.univaq.sose.LegacyBoxOfficeProviderSOAP.repository.BoxOfficeRepository;
import jakarta.jws.WebService;

import java.sql.SQLException;
import java.util.List;

@WebService(endpointInterface = "it.univaq.sose.LegacyBoxOfficeProviderSOAP.service.BoxOfficeService")
public class BoxOfficeServiceImpl implements BoxOfficeService {

	private final BoxOfficeRepository repository;

	public BoxOfficeServiceImpl() {
		this.repository = new BoxOfficeRepository();
	}

	@Override
	public List<Event> getAllEvents() {
		try {
			return repository.getAllEvents();
		} catch (SQLException e) {
			throw new RuntimeException("Database error while fetching events", e);
		}
	}

	@Override
	public Event getEventByID(int eventGlobalId) throws BoxOfficeException {
		try {
			Event event = repository.getEventByGlobalID(eventGlobalId);
			if (event == null) {
				throw new BoxOfficeException("Event not found with Global ID: " + eventGlobalId);
			}
			return event;
		} catch (SQLException e) {
			throw new BoxOfficeException("Database error while fetching event " + eventGlobalId + ": " + e.getMessage());
		}
	}

	@Override
	public List<Event> searchByName(String name) {
		try {
			return repository.searchByName(name);
		} catch (SQLException e) {
			throw new RuntimeException("Database error while searching events by name", e);
		}
	}

	@Override
	public List<Ticket> getEventTickets(int eventGlobalId) throws BoxOfficeException {
		getEventByID(eventGlobalId);
		try {
			return repository.getEventTickets(eventGlobalId);
		} catch (SQLException e) {
			throw new BoxOfficeException("Database error while fetching tickets for event " + eventGlobalId + ": " + e.getMessage());
		}
	}

	@Override
	public Ticket getTicket(int eventGlobalId, int ticketId) throws BoxOfficeException {
		try {
			Ticket ticket = repository.getTicket(eventGlobalId, ticketId);
			if (ticket == null) {
				throw new BoxOfficeException("Ticket " + ticketId + " not found for event with Global ID: " + eventGlobalId);
			}
			return ticket;
		} catch (SQLException e) {
			throw new BoxOfficeException("Database error while fetching ticket: " + e.getMessage());
		}
	}
}