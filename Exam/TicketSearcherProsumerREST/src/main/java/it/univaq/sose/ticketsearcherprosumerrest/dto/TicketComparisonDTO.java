package it.univaq.sose.ticketsearcherprosumerrest.dto;

import java.util.List;

public class TicketComparisonDTO {
    private int eventGlobalId;
    private List<TicketOfferDTO> officialTickets;
    private List<TicketOfferDTO> resaleTickets;
    private TicketOfferDTO cheapestOverall;

    public TicketComparisonDTO() {}

    public TicketComparisonDTO(int eventGlobalId, List<TicketOfferDTO> officialTickets,
                               List<TicketOfferDTO> resaleTickets, TicketOfferDTO cheapestOverall) {
        this.eventGlobalId = eventGlobalId;
        this.officialTickets = officialTickets;
        this.resaleTickets = resaleTickets;
        this.cheapestOverall = cheapestOverall;
    }

    public int getEventGlobalId() { return eventGlobalId; }
    public void setEventGlobalId(int eventGlobalId) { this.eventGlobalId = eventGlobalId; }
    public List<TicketOfferDTO> getOfficialTickets() { return officialTickets; }
    public void setOfficialTickets(List<TicketOfferDTO> officialTickets) { this.officialTickets = officialTickets; }
    public List<TicketOfferDTO> getResaleTickets() { return resaleTickets; }
    public void setResaleTickets(List<TicketOfferDTO> resaleTickets) { this.resaleTickets = resaleTickets; }
    public TicketOfferDTO getCheapestOverall() { return cheapestOverall; }
    public void setCheapestOverall(TicketOfferDTO cheapestOverall) { this.cheapestOverall = cheapestOverall; }
}