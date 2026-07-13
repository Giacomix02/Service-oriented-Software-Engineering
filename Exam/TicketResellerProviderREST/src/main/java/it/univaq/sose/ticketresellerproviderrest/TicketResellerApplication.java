package it.univaq.sose.ticketresellerproviderrest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class TicketResellerApplication extends Application {
    // No code is needed inside this class.
    // The @ApplicationPath annotation tells the server that this is the base URL for the REST API.
}