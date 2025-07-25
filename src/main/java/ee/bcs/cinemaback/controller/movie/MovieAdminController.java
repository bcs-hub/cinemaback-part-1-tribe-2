package ee.bcs.cinemaback.controller.movie;


import ee.bcs.cinemaback.service.movie.MovieService;
import ee.bcs.cinemaback.service.movie.dto.MovieAdminSummary;
import ee.bcs.cinemaback.service.movie.dto.MovieDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/v1/movie")
@RestController
@RequiredArgsConstructor
public class MovieAdminController {

    private final MovieService movieService;


    @GetMapping()
    @Operation(summary = "Returns IDs of all movies",
            description = "If there are no movies, returns an empty array")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")})
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Returns movie information by id",
            description = "If the movie does not exist, responds with error code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Movie already exists")})
    public MovieDto getMovie(@PathVariable("id") Integer id) {
        return movieService.getMovie(id);
    }


    @PostMapping("/add")
    @Operation(summary = "Adds a new movie",
            description = """
                    A new movie is created in the system.
                    If the movie already exists, throws an error with error code 409 (CONFLICT)""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Movie already exists")})
    public void addNewMovie(@RequestBody MovieDto movieDto) {
        movieService.addNewMovie(movieDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates movie information.",
            description = """
                    Updates movie information in the system.
                    If the movie name already exists, throws an error with error code 409 (CONFLICT)""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Movie already exists")})
    public void updateMovie(@PathVariable("id") Integer id, @RequestBody MovieDto movieDto) {
        movieService.updateMovie(id, movieDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a movie.",
            description = """
                    Deletes the movie in the system.
                    If the movie is associated with screenings, throws an error with error code 409 (CONFLICT)""")
    public void deleteMovie(@PathVariable("id") Integer id) {
        movieService.deleteMovie(id);
    }

    @GetMapping("/summary")
    @Operation(summary = "Returns admin summary of all movies",
            description = "If there are no movies, returns an empty array")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")})
    public List<MovieAdminSummary> getMovieAdminSummary() {
        return movieService.getMovieAdminSummary();
    }
}
