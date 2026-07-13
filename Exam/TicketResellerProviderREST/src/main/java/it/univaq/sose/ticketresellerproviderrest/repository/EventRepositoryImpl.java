package it.univaq.sose.ticketresellerproviderrest.repository;

import it.univaq.sose.ticketresellerproviderrest.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRepositoryImpl implements EventRepository {

    @Override
    public boolean existsByGlobalId(int eventGlobalId) {
        String query = "SELECT 1 FROM Event WHERE Event_Global_ID = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, eventGlobalId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}