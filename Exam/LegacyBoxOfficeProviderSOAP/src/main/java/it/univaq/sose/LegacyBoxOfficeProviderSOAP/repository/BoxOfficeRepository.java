package it.univaq.sose.LegacyBoxOfficeProviderSOAP.repository;

import it.univaq.sose.LegacyBoxOfficeProviderSOAP.model.Event;
import it.univaq.sose.LegacyBoxOfficeProviderSOAP.model.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoxOfficeRepository {

    private static final String DB_HOST = System.getenv().getOrDefault("MYSQL_HOST", "localhost");
    private static final String DB_PORT = System.getenv().getOrDefault("MYSQL_PORT", "3306");
    private static final String DB_NAME = System.getenv().getOrDefault("MYSQL_DB", "legacy_box_office_db");
    private static final String DB_USER = System.getenv().getOrDefault("MYSQL_USER", "root");
    private static final String DB_PASS = System.getenv().getOrDefault("MYSQL_PASSWORD", "root");

    private static final String DB_URL =
            "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private Event mapEvent(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setId(rs.getInt("ID"));
        event.setEventGlobalId(rs.getInt("Event_Global_ID"));
        event.setName(rs.getString("Name"));
        event.setArtistName(rs.getString("Artist_Name"));
        event.setLocation(rs.getString("Location"));
        event.setDescription(rs.getString("Description"));
        return event;
    }

    private Ticket mapTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("ID"));
        ticket.setPrice(rs.getFloat("Price"));
        ticket.setSeat(rs.getString("Seat"));
        ticket.setEventId(rs.getInt("Event_Global_ID"));
        return ticket;
    }

    public List<Event> getAllEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM Event";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                events.add(mapEvent(rs));
            }
        }
        return events;
    }

    public Event getEventByGlobalID(int eventGlobalId) throws SQLException {
        String query = "SELECT * FROM Event WHERE Event_Global_ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, eventGlobalId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapEvent(rs);
                }
            }
        }
        return null;
    }

    public List<Event> searchByName(String name) throws SQLException {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM Event WHERE LOWER(Name) LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + (name == null ? "" : name.toLowerCase()) + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    events.add(mapEvent(rs));
                }
            }
        }
        return events;
    }

    public List<Ticket> getEventTickets(int eventGlobalId) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String query = """
                SELECT t.ID, t.Price, t.Seat, e.Event_Global_ID
                FROM Ticket t
                JOIN Event e ON t.Event_ID = e.ID
                WHERE e.Event_Global_ID = ?
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, eventGlobalId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(mapTicket(rs));
                }
            }
        }
        return tickets;
    }

    public Ticket getTicket(int eventGlobalId, int ticketId) throws SQLException {
        String query = """
                SELECT t.ID, t.Price, t.Seat, e.Event_Global_ID
                FROM Ticket t
                JOIN Event e ON t.Event_ID = e.ID
                WHERE e.Event_Global_ID = ? AND t.ID = ?
                """;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, eventGlobalId);
            stmt.setInt(2, ticketId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapTicket(rs);
                }
            }
        }
        return null;
    }
}