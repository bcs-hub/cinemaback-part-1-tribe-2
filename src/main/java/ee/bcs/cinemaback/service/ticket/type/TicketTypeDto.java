package ee.bcs.cinemaback.service.ticket.type;

import ee.bcs.cinemaback.persistence.ticket.type.TicketType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link TicketType} entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketTypeDto implements Serializable {
    private Integer id;
    @Size(max = 255)
    @NotNull
    @NotEmpty(message = "Ticket type name cannot be empty")
    private String name;
    @NotNull
    @Size(max = 255, message = "Ticket type price must be between 0 and 255")
    private BigDecimal price;
}
