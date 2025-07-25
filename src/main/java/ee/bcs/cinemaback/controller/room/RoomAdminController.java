package ee.bcs.cinemaback.controller.room;


import ee.bcs.cinemaback.service.room.RoomService;
import ee.bcs.cinemaback.service.room.dto.RoomDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/v1/room")
public class RoomAdminController {

    private final RoomService roomService;


    public RoomAdminController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    @Operation(summary = "Returns all rooms",
            description = "If there are no rooms, an empty array is returned.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")})
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }


    @PostMapping
    @Operation(summary = "Adds a new room with seats",
            description = """
                    A new room with seats is created in the system.
                    If the room already exists, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Room already exists")})
    public void addRoom(@RequestBody RoomDto roomDto) {
        roomService.addRoom(roomDto);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Updates the room name.",
            description = """
                    The room name is updated in the system.
                    If the room does not exist, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Room already exists")})
    public void updateRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        roomService.updateRoom(id, roomDto);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a room.",
            description = """
                    The room is deleted from the system.
                    If the room is associated with screenings, an error with error code 409 (CONFLICT) is thrown.""")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "409", description = "Room is associated with screenings")})
    public void deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
    }


}
