package it.univaq.sose.ticketsearcherprosumerrest.dto;

public class TicketSearchRequestDTO {
    private String eventName;
    private String resellerType;

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }
    public String getResellerType() { return resellerType; }
    public void setResellerType(String resellerType) { this.resellerType = resellerType; }
}