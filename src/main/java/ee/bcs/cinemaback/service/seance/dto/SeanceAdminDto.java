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
public class SeanceAdminDto implements Serializable {
    private Integer roomId;
    private Integer movieId;
    @NotNull
    private String dateTime;
    @Size(max = 255)
    @NotNull
    private String language;
    @Size(max = 255)
    @NotNull
    private String subtitles;
}
