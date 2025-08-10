package ee.bcs.cinemaback.service.movie.dto;

import ee.bcs.cinemaback.persistence.movie.Movie;
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
public class MovieDto implements Serializable {
    private Integer id;
    private String title;
    private Integer runtime;
    private String director;
    private Integer genreId;  //added
    private String genreName;
    private String posterImage;
    private String youtubeLink;
    private String description;
}
