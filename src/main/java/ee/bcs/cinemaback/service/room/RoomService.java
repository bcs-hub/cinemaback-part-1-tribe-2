package ee.bcs.cinemaback.service.room;

import ee.bcs.cinemaback.infrastructure.exception.DatabaseConstraintException;
import ee.bcs.cinemaback.infrastructure.exception.DatabaseNameConflictException;
import ee.bcs.cinemaback.infrastructure.exception.ResourceNotFoundException;
import ee.bcs.cinemaback.persistence.room.Room;
import ee.bcs.cinemaback.persistence.room.RoomRepository;
import ee.bcs.cinemaback.persistence.room.seat.Seat;
import ee.bcs.cinemaback.persistence.seance.Seance;
import ee.bcs.cinemaback.persistence.seance.SeanceRepository;
import ee.bcs.cinemaback.persistence.ticket.Ticket;
import ee.bcs.cinemaback.persistence.ticket.TicketRepository;
import ee.bcs.cinemaback.service.room.dto.RoomDto;
import ee.bcs.cinemaback.service.room.dto.RoomSeanceDto;
import ee.bcs.cinemaback.service.room.dto.SeatDto;
import jakarta.transaction.Transactional;   // incompatible
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static ee.bcs.cinemaback.infrastructure.Error.ROOM_CANNOT_BE_DELETED;
import static ee.bcs.cinemaback.infrastructure.Error.ROOM_SEATS_CANNOT_BE_EDITED;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final SeanceRepository seanceRepository;
    private final TicketRepository ticketRepository;
    private final RoomMapper roomMapper;


    public void addRoom(RoomDto roomDto) {

        try {
            Room room = roomMapper.toRoom(roomDto);
            validateRoomName(room.getName());
            roomRepository.save(room);
            updateSeats(room);
            roomRepository.save(room);
        } catch (DatabaseConstraintException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateSeats(Room room) {

        room.getSeats().clear();
        if (seanceRepository.existsByRoomId(room.getId())) {
            throw new DatabaseConstraintException(ROOM_SEATS_CANNOT_BE_EDITED.getMessage());
        }


        for (int i = 0; i < room.getRows(); i++) {
            for (int j = 0; j < room.getCols(); j++) {
                Seat seat = new Seat();
                seat.setRow(i + 1);
                seat.setCol(j + 1);
                seat.setRoom(room);
                room.getSeats().add(seat);
            }
        }
    }

    public void deleteRoom(int roomId) {

        if (seanceRepository.existsByRoomId(roomId)) {
            throw new DatabaseConstraintException(ROOM_CANNOT_BE_DELETED.getMessage());
        }

        roomRepository.deleteById(roomId);
    }

    public List<RoomDto> getAllRooms() {
        return roomMapper.toRoomDtos(roomRepository.findAllAlphabetic());
    }

    private void validateRoomName(String roomName) {
        if (roomName == null || roomName.isEmpty()) {
            throw new DatabaseConstraintException("Room name cannot be empty");
        }
        if (roomRepository.existsBy(roomName)) {
            throw new DatabaseNameConflictException("A room with this name already exists");
        }
    }

    private void validateRoomName(String newName, String oldName) {

        if (newName == null || newName.isEmpty()) {
            throw new DatabaseConstraintException("Room name cannot be empty");
        }

        if (!newName.equals(oldName) && roomRepository.existsBy(newName)) {
            throw new DatabaseNameConflictException("A room with this name already exists");
        }
    }

    @Transactional
    public void updateRoom(Integer id, RoomDto roomDto) {
        Room existingRoom = updateName(id, roomDto);

        updateSeats(roomDto, existingRoom);
    }

    public RoomDto getRoom(Integer id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        return roomMapper.toRoomDto(room);
    }

    public RoomSeanceDto getRoomSeance(Integer seanceId) {
        Seance seance = seanceRepository.findById(seanceId).orElseThrow(
                () -> new ResourceNotFoundException("Seance not found")
        );
        Room room = seance.getRoom();

        List<Ticket> tickets = ticketRepository.findSeanceTickets(seanceId);
        List<SeatDto> seatDTOs = createSeatDtos(room.getSeats(), tickets);

        return createRoomSeanceDto(room, seatDTOs);
    }

    private List<SeatDto> createSeatDtos(List<Seat> seats, List<Ticket> tickets) {
        return seats.stream()
                .map(seat -> {
                    SeatDto seatDto = new SeatDto();
                    seatDto.setId(seat.getId());
                    seatDto.setRow(seat.getRow());
                    seatDto.setCol(seat.getCol());
                    seatDto.setAvailable(!isSeatBooked(seat, tickets));
                    return seatDto;
                }).toList();
    }

    private boolean isSeatBooked(Seat seat, List<Ticket> tickets) {
        return tickets.stream().anyMatch(ticket -> ticket.getSeat().getId().equals(seat.getId()));
    }

    private RoomSeanceDto createRoomSeanceDto(Room room, List<SeatDto> seatDTOs) {
        RoomSeanceDto roomSeanceDto = new RoomSeanceDto();
        roomSeanceDto.setSeats(seatDTOs);
        roomSeanceDto.setRows(room.getRows());
        roomSeanceDto.setCols(room.getCols());
        roomSeanceDto.setRoomName(room.getName());
        return roomSeanceDto;
    }

    private void updateSeats(RoomDto roomDto, Room existingRoom) {
        if(existingRoom.getRows().equals(roomDto.getRows()) && existingRoom.getCols().equals(roomDto.getCols())) {
            return;
        }

        existingRoom.setRows(roomDto.getRows());
        existingRoom.setCols(roomDto.getCols());
        updateSeats(existingRoom);
        roomRepository.save(existingRoom);
    }

    private Room updateName(Integer id, RoomDto roomDto) {
        Room existingRoom = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        validateRoomName(roomDto.getName(), existingRoom.getName());
        existingRoom.setName(roomDto.getName());
        roomRepository.save(existingRoom);
        return existingRoom;
    }
}
