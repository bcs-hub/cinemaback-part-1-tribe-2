package ee.bcs.cinemaback.service.ticket;

import ee.bcs.cinemaback.persistence.ticket.Ticket;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Ticket} entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketPurchaseDto implements Serializable {
    private  Integer userId;
    @NotNull
    private  Integer seatCol;
    @NotNull
    private  Integer seatRow;
    private  Integer seanceId;
    @Size(max = 255)
    @NotNull
    private  String seanceRoomName;
    @Size(max = 255)
    @NotNull
    private  String ticketTypeName;
}
