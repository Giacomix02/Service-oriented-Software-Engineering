package it.univaq.sose.LegacyBoxOfficeProviderSOAP.service;

import it.univaq.sose.LegacyBoxOfficeProviderSOAP.BoxOfficeException;
import it.univaq.sose.LegacyBoxOfficeProviderSOAP.model.Event;
import it.univaq.sose.LegacyBoxOfficeProviderSOAP.model.Ticket;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.util.List;

@WebService(name = "BoxOfficeService")
public interface BoxOfficeService {
	@WebMethod
	List<Event> getAllEvents();

	@WebMethod
	Event getEventByID(int eventGlobalId) throws BoxOfficeException;

	@WebMethod
	List<Event> searchByName(String name);

	@WebMethod
	List<Ticket> getEventTickets(int eventGlobalId) throws BoxOfficeException;

	@WebMethod
	Ticket getTicket(int eventGlobalId, int ticketId) throws BoxOfficeException;
}
