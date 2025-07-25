package ee.bcs.cinemaback.controller.genre;

import ee.bcs.cinemaback.service.genre.GenreDto;
import ee.bcs.cinemaback.service.genre.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin/v1/genre")
@RestController
@RequiredArgsConstructor
public class GenreAdminController {
    private final GenreService genreService;

    @GetMapping()
    @Operation(
            summary = "Fetches all genres from the system (genre table in the database).",
            description = "Returns information including genreId and genreName.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")})
    public List<GenreDto> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns the genre name by id.",
            description = "If the genre does not exist, responds with error code 409 (CONFLICT).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Genre does not exist")})
    public String getGenre(@PathVariable("id") Integer id) {
        return genreService.getGenreName(id);
    }

    @PostMapping
    @Operation(summary = "Adds a new genre.",
            description = """
                    Creates a new genre in the system.
                    If the genre already exists, responds with error code 409 (CONFLICT).""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Genre already exists")})
    public void addGenre(@RequestParam String genreName) {
        genreService.addGenre(genreName);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates the genre name.",
            description = """
                    Updates the genre name in the system.
                    If the genre does not exist, responds with error code 409 (CONFLICT).""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Genre does not exist")})
    public void updateGenre(@PathVariable("id") Integer id, @RequestParam String genreName) {
        genreService.updateGenreName(id, genreName);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes the genre by id. If the genre is associated with a movie, deletion is not allowed.",
            description = """
                    Deletes the genre name from the system.
                    If the genre does not exist, responds with error code 409 (CONFLICT).""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")})
    public void deleteGenre(@PathVariable("id") Integer id) {
        genreService.deleteGenreBy(id);
    }
}
