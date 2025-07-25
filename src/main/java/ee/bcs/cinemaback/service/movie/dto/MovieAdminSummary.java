package ee.bcs.cinemaback.service.movie.dto;

import ee.bcs.cinemaback.persistence.movie.Movie;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Movie} entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieAdminSummary implements Serializable {
    private Integer id;
    @Size(max = 255)
    @NotNull
    private String genreName;
    @Size(max = 255)
    @NotNull
    private String title;
    private long numberOfSeances;

}
