package it.univaq.sose.ticketsearcherprosumerrest.dto;

public class TicketOfferDTO {
    public enum Source { OFFICIAL, RESELLER }

    private int id;
    private float price;
    private String seat;
    private int eventId;
    private Source source;

    public TicketOfferDTO() {}

    public TicketOfferDTO(int id, float price, String seat, int eventId, Source source) {
        this.id = id;
        this.price = price;
        this.seat = seat;
        this.eventId = eventId;
        this.source = source;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public String getSeat() { return seat; }
    public void setSeat(String seat) { this.seat = seat; }
    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public Source getSource() { return source; }
    public void setSource(Source source) { this.source = source; }
}