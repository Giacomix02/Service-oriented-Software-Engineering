package it.univaq.sose.ticketsearcherprosumerrest.dto.fromProviders;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketResellerOfferDTOProvider {
    private int id;
    private float price;
    private String seat;

    @JsonProperty("Event_Global_ID")
    private int eventId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public String getSeat() { return seat; }
    public void setSeat(String seat) { this.seat = seat; }
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
}