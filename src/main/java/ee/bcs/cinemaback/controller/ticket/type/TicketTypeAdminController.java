package ee.bcs.cinemaback.controller.ticket.type;


import ee.bcs.cinemaback.service.ticket.type.TicketTypeDto;
import ee.bcs.cinemaback.service.ticket.type.TicketTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/v1/ticket/type")
@RestController
public class TicketTypeAdminController {
    private final TicketTypeService ticketService;

    public TicketTypeAdminController(TicketTypeService ticketService) {
        this.ticketService = ticketService;
    }


    @GetMapping()
    public List<TicketTypeDto> getAllTicketTypes() {
        return ticketService.getAllTicketTypes();
    }

    @PostMapping("/add")
    @Operation(summary = "Adds a new ticket type",
            description = """
                    A new ticket type is created in the system.
                    If the ticket type already exists, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Ticket type already exists")})
    public void addTicketType(@RequestBody TicketTypeDto ticketTypeDto) {
        ticketService.addTicketType(ticketTypeDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates the ticket type name.",
            description = """
                    The ticket type name is updated in the system.
                    If the ticket type does not exist, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Ticket type already exists")})
    public void updateTicketType(@PathVariable("id") Integer id, @RequestBody TicketTypeDto ticketTypeDto) {
        ticketService.updateTicketType(id, ticketTypeDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes the ticket type.",
            description = """
                    The ticket type is deleted in the system.
                    If the ticket type is associated with an active ticket, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Ticket type is associated with an active ticket")})
    public void deleteTicketType(@PathVariable Integer id) {
        ticketService.deleteTicketType(id);
    }

}
