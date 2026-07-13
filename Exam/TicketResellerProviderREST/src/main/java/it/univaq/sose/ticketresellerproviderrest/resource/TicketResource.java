package it.univaq.sose.ticketresellerproviderrest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@Path("/")
@Tag(name = "Ticket Reseller", description = "Endpoints for secondary market ticket resale")
public interface TicketResource {

    @GET
    @Path("/getEventTickets/{Event_Global_ID}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all resale tickets", description = "Returns a list of all resale tickets for a specific Event_Global_ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of tickets successfully retrieved (may be empty)"),
            @ApiResponse(responseCode = "404", description = "The specified event does not exist")
    })
    Response getEventTickets(
            @Parameter(description = "Global ID of the event", required = true)
            @PathParam("Event_Global_ID") int eventGlobalId);

    @GET
    @Path("/getTicket/{Event_Global_ID}/{Ticket_ID}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get resale ticket details", description = "Finds a specific ticket for an event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket found and returned"),
            @ApiResponse(responseCode = "404", description = "Ticket not found for the given event")
    })
    Response getTicketGet(
            @Parameter(description = "Global ID of the event", required = true)
            @PathParam("Event_Global_ID") int eventGlobalId,
            @Parameter(description = "Local ID of the ticket", required = true)
            @PathParam("Ticket_ID") int ticketId);
}