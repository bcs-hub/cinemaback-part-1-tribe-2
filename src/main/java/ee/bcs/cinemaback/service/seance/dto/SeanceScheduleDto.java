package ee.bcs.cinemaback.service.seance.dto;

import ee.bcs.cinemaback.persistence.seance.Seance;
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
public class SeanceScheduleDto implements Serializable {

    private Integer seanceId;
    private String roomName;
    private Integer movieId;
    private String movieGenreName;
    private Integer movieRuntime;
    private String movieTitle;
    private String moviePosterImage;
    private String dateTime;
    private String language;
    private String subtitles;
    private String movieYoutubeLink;
    private Integer totalSeats;
    private Integer availableSeats;
}
