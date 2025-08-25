package ee.bcs.cinemaback.service.movie;

import ee.bcs.cinemaback.infrastructure.exception.DatabaseConstraintException;
import ee.bcs.cinemaback.service.movie.dto.MovieDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @InjectMocks
    private MovieService movieService;


    @Test
    public void testInvalidYoutubeLink() {
        MovieDto movieDto = new MovieDto();
        movieDto.setYoutubeLink("https://www.test.com/something/wrong");

        assertThrows(DatabaseConstraintException.class, () -> movieService.validateYoutubeLink(movieDto));
    }

    @Test
    public void testValidYoutubeLink() {
        MovieDto movieDto = new MovieDto();
        movieDto.setYoutubeLink("https://www.youtube.com/embed/correctxxxx");
        assertTrue(movieService.validateYoutubeLink(movieDto));
    }
}
