package ee.bcs.cinemaback.service.seance.dto;

import ee.bcs.cinemaback.persistence.seance.Seance;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Seance} entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeanceAdminSummary implements Serializable {
    private Integer id;
    @Size(max = 255)
    @NotNull
    private String roomName;
    @Size(max = 255)
    @NotNull
    private String movieTitle;
    @NotNull
    private String dateTime;
    private Integer totalSeats;
    private Integer availableSeats;

}
