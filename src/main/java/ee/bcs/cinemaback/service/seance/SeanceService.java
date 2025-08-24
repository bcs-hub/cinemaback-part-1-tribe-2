package ee.bcs.cinemaback.service.seance;

import ee.bcs.cinemaback.infrastructure.exception.DatabaseConstraintException;
import ee.bcs.cinemaback.infrastructure.exception.DatabaseNameConflictException;
import ee.bcs.cinemaback.infrastructure.exception.ResourceNotFoundException;
import ee.bcs.cinemaback.persistence.movie.MovieRepository;
import ee.bcs.cinemaback.persistence.room.RoomRepository;
import ee.bcs.cinemaback.persistence.seance.Seance;
import ee.bcs.cinemaback.persistence.seance.SeanceRepository;
import ee.bcs.cinemaback.persistence.ticket.TicketRepository;
import ee.bcs.cinemaback.service.seance.dto.SeanceAdminDto;
import ee.bcs.cinemaback.service.seance.dto.SeanceAdminSummary;
import ee.bcs.cinemaback.service.seance.dto.SeanceScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static ee.bcs.cinemaback.infrastructure.Error.*;
import static ee.bcs.cinemaback.infrastructure.Status.ACTIVE;
import static ee.bcs.cinemaback.infrastructure.Status.DELETED;


@Service
@RequiredArgsConstructor
public class SeanceService {

    private final SeanceRepository seanceRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final TicketRepository ticketRepository;
    private final SeanceMapper seanceMapper;
    private final Clock clock;


    public List<SeanceScheduleDto> findAllFutureSeances() {
        List<Seance> seances = seanceRepository.findByStartTimeGreaterThan(clock.instant());
        return getSeanceScheduleDtos(seances);

    }

    public List<SeanceScheduleDto> findMovieAllFutureSeances(Integer movieId) {
        List<Seance> seances = seanceRepository.findByStartTimeGreaterThanAndMovieId(clock.instant(), movieId);
        return getSeanceScheduleDtos(seances);

    }

    public void createSeance(SeanceAdminDto seanceAdminDto) {
        Seance seance = seanceMapper.toSeance(seanceAdminDto);

        seance.setRoom(roomRepository.findById(seanceAdminDto.getRoomId()).orElseThrow(
                () -> new ResourceNotFoundException(ROOM_NOT_FOUND.getMessage())));
        seance.setMovie(movieRepository.findById(seanceAdminDto.getMovieId()).orElseThrow(
                () -> new ResourceNotFoundException(MOVIE_NOT_FOUND.getMessage())));

        checkSeancesTimeAndRoom(seance);

        seance.setStatus(ACTIVE.getLetter());
        seanceRepository.save(seance);
    }

    private void checkSeancesTimeAndRoom(Seance seance) {
        List<Seance> activeSeances = seanceRepository.findAllSeancesBy(ACTIVE.getLetter());

        Instant newSeanceStart = seance.getStartTime();
        int newSeanceRuntimeMinutes = seance.getMovie().getRuntime();
        int cleanUpTimeMinutes = 20;
        Instant newSeanceEnd = newSeanceStart.plusSeconds(
                (long) (newSeanceRuntimeMinutes + cleanUpTimeMinutes) * 60);

        for (Seance existing : activeSeances) {
            Instant existingSeanceStartTime = existing.getStartTime();
            int existingSeanceRuntimeMinutes = existing.getMovie().getRuntime();
            Instant existingSeanceEndTime = existingSeanceStartTime.plusSeconds(
                    (long) (existingSeanceRuntimeMinutes + cleanUpTimeMinutes) * 60);

            boolean sameRoom = seanceRepository.existsByRoomId(seance.getRoom().getId());
            boolean overlaps = newSeanceStart.isBefore(existingSeanceEndTime) && newSeanceEnd.isAfter(existingSeanceStartTime);

            if (sameRoom && overlaps) {
                throw new DatabaseNameConflictException(SEANCE_TIME_OVERLAP.getMessage());
            }
        }
    }

    public List<SeanceAdminSummary> getSeanceAdminSummary() {
        List<Seance> seances = seanceRepository.findAllSeancesBy(ACTIVE.getLetter());
        List<SeanceAdminSummary> seanceAdminSummaries = new ArrayList<>();

        for (Seance seance : seances) {
            Integer totalSeats = seance.getRoom().getCols() * seance.getRoom().getRows();
            Integer bookedSeats = ticketRepository.countBySeance(seance.getId());
            SeanceAdminSummary seanceAdminSummary = seanceMapper.toAdminSummary(seance);
            seanceAdminSummary.setTotalSeats(totalSeats);
            seanceAdminSummary.setAvailableSeats(totalSeats - bookedSeats);
            seanceAdminSummaries.add(seanceAdminSummary);
        }

        return seanceAdminSummaries;
    }

    public SeanceScheduleDto getSeanceScheduleDto(Integer id) {
        Seance seance = getSeance(id);

        Integer totalSeats = seance.getRoom().getCols() * seance.getRoom().getRows();
        Integer bookedSeats = ticketRepository.countBySeance(seance.getId());
        SeanceScheduleDto seanceScheduleDto = seanceMapper.toScheduleDto(seance);

        seanceScheduleDto.setTotalSeats(totalSeats);
        seanceScheduleDto.setAvailableSeats(totalSeats - bookedSeats);


        return seanceScheduleDto;
    }

    public SeanceAdminDto getSeanceAdminDto(Integer id) {
        Seance seance = getSeance(id);

        return seanceMapper.toAdminDto(seance);
    }

    public Seance getSeance(Integer id) {
        Seance seance = seanceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Seance not found")
        );
        return seance;
    }

    public void updateSeance(Integer id, SeanceAdminDto seanceAdminDto) {

        Seance seance = getSeanceAndValidateChangeable(id);
        seanceMapper.updateSeanceFromDto(seanceAdminDto, seance);
        seance.setRoom(roomRepository.findById(seanceAdminDto.getRoomId()).orElseThrow(() -> new ResourceNotFoundException(ROOM_NOT_FOUND.getMessage())));

        seance.setMovie(movieRepository.findById(seanceAdminDto.getMovieId()).orElseThrow(() -> new ResourceNotFoundException(MOVIE_NOT_FOUND.getMessage())));

        seanceRepository.save(seance);
    }

    public void deleteSeance(Integer id) {
        Seance seance = getSeanceAndValidateChangeable(id);
        seance.setStatus(DELETED.getLetter());
        seanceRepository.save(seance);
    }

    private List<SeanceScheduleDto> getSeanceScheduleDtos(List<Seance> seances) {
        List<SeanceScheduleDto> seanceScheduleDtos = new ArrayList<>();

        for (Seance seance : seances) {
            SeanceScheduleDto seanceScheduleDto = seanceMapper.toScheduleDto(seance);
            int totalSeats = seance.getRoom().getRows() * seance.getRoom().getCols();
            int bookedSeats = ticketRepository.countBySeance(seance.getId());
            seanceScheduleDto.setTotalSeats(totalSeats);
            seanceScheduleDto.setAvailableSeats(totalSeats - bookedSeats);
            seanceScheduleDtos.add(seanceScheduleDto);
        }
        return seanceScheduleDtos;
    }

    private Seance getSeanceAndValidateChangeable(Integer id) {
        Seance seance = getSeance(id);

        if (ticketRepository.existsBySeanceId(id) && clock.instant().isBefore(seance.getStartTime())) {
            throw new DatabaseConstraintException(SEANCE_HAS_ACTIVE_TICKETS.getMessage());
        }
        return seance;
    }


}
