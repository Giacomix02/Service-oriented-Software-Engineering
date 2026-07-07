package it.univaq.sose.LegacyBoxOfficeProviderSOAP.model;

public class Ticket {
    private int id;
    private float price;
    private String seat;
    private int eventId;

    public Ticket() {}

    public Ticket(int id, float price, String seat, int eventId) {
        this.id = id;
        this.price = price;
        this.seat = seat;
        this.eventId = eventId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public String getSeat() { return seat; }
    public void setSeat(String seat) { this.seat = seat; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
}
