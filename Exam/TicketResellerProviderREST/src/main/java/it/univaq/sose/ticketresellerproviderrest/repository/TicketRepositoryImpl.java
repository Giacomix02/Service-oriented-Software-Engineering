package it.univaq.sose.ticketresellerproviderrest.repository;

import it.univaq.sose.ticketresellerproviderrest.model.Ticket;
import it.univaq.sose.ticketresellerproviderrest.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketRepositoryImpl implements TicketRepository {

    @Override
    public List<Ticket> getTicketsByEvent(int eventGlobalId) {
        List<Ticket> tickets = new ArrayList<>();

        String query = "SELECT t.ID, t.Price, t.Seat, e.Event_Global_ID " +
                "FROM Ticket t " +
                "JOIN Event e ON t.Event_ID = e.ID " +
                "WHERE e.Event_Global_ID = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, eventGlobalId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setId(rs.getInt("ID"));
                    ticket.setPrice(rs.getFloat("Price"));
                    ticket.setSeat(rs.getString("Seat"));
                    ticket.setEventId(rs.getInt("Event_Global_ID"));

                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    @Override
    public Ticket getTicket(int eventGlobalId, int ticketId) {
        Ticket ticket = null;

        String query = "SELECT t.ID, t.Price, t.Seat, e.Event_Global_ID " +
                "FROM Ticket t " +
                "JOIN Event e ON t.Event_ID = e.ID " +
                "WHERE e.Event_Global_ID = ? AND t.ID = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, eventGlobalId);
            stmt.setInt(2, ticketId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ticket = new Ticket();
                    ticket.setId(rs.getInt("ID"));
                    ticket.setPrice(rs.getFloat("Price"));
                    ticket.setSeat(rs.getString("Seat"));
                    ticket.setEventId(rs.getInt("Event_Global_ID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticket;
    }
}