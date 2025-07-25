package ee.bcs.cinemaback.controller.seance;

import ee.bcs.cinemaback.persistence.seance.Seance;
import ee.bcs.cinemaback.service.seance.SeanceService;
import ee.bcs.cinemaback.service.seance.dto.SeanceAdminDto;
import ee.bcs.cinemaback.service.seance.dto.SeanceAdminSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/v1/seance")
public class SeanceAdminController {

    private final SeanceService seanceService;

    public SeanceAdminController(SeanceService seanceService) {
        this.seanceService = seanceService;
    }


    @PostMapping
    @Operation(summary = "Adds a new seance",
            description = """
                    A new seance is created in the system.
                    If the seance already exists, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "CONFLICT")})
    public void createSeance(@RequestBody SeanceAdminDto seanceAdminDto) {
        seanceService.createSeance(seanceAdminDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a seance",
            description = """
                    Returns a seance.
                    If the seance already exists, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")})
    public SeanceAdminDto getSeanceAdminDto(@PathVariable("id") Integer id) {
        return seanceService.getSeanceAdminDto(id);
    }

    @GetMapping("/{id}/entity")
    @Operation(summary = "Returns a seance entity",
            description = """
                    Returns a seance.
                    If the seance already exists, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")})
    public Seance getSeanceEntity(@PathVariable("id") Integer id) {
        return seanceService.getSeance(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a seance",
            description = """
                    Updates a seance.
                    If the seance already exists, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")})
    public void updateSeance(@PathVariable("id") Integer id, @RequestBody SeanceAdminDto seanceAdminDto) {
        seanceService.updateSeance(id, seanceAdminDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a seance",
            description = """
                    Deletes a seance.
                    If the seance has active tickets, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "CONFLICT")})
    public void deleteSeance(@PathVariable("id") Integer id) {
        seanceService.deleteSeance(id);
    }

    @GetMapping("/summary")
    @Operation(summary = "Returns a summary of all seances",
            description = """
                    Returns a summary of all seances.
                    If there are no seances, responds with an empty array.""")
    public List<SeanceAdminSummary> getSeanceAdminSummary() {
        return seanceService.getSeanceAdminSummary();
    }

}
