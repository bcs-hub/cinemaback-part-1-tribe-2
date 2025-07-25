package ee.bcs.cinemaback.service.ticket;

import ee.bcs.cinemaback.persistence.ticket.Ticket;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Ticket} entity
 */
@Data
public class TicketDto implements Serializable {
    @NotNull
    private final Integer seatCol;
    @NotNull
    private final Integer seatRow;
    @Size(max = 255)
    @NotNull
    private final String seanceRoomName;
    @Size(max = 255)
    @NotNull
    private final String seanceMovieTitle;
    @NotNull
    private final String seanceStartTime;
    @Size(max = 255)
    @NotNull
    private final String ticketTypeName;
}
