package ee.bcs.cinemaback.service.ticket;

import ee.bcs.cinemaback.infrastructure.exception.ResourceNotFoundException;
import ee.bcs.cinemaback.persistence.room.seat.Seat;
import ee.bcs.cinemaback.persistence.room.seat.SeatRepository;
import ee.bcs.cinemaback.persistence.seance.Seance;
import ee.bcs.cinemaback.persistence.seance.SeanceRepository;
import ee.bcs.cinemaback.persistence.ticket.Ticket;
import ee.bcs.cinemaback.persistence.ticket.TicketRepository;
import ee.bcs.cinemaback.persistence.ticket.type.TicketType;
import ee.bcs.cinemaback.persistence.ticket.type.TicketTypeRepository;
import ee.bcs.cinemaback.persistence.user.User;
import ee.bcs.cinemaback.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

import static ee.bcs.cinemaback.infrastructure.Error.*;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final SeanceRepository seanceRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketMapper ticketMapper;
    private final Clock clock;

    public List<TicketDto> getAllActiveUserTickets(Integer userId) {
        List<Ticket> tickets = ticketRepository.getAllActiveUserTickets(userId, clock.instant());
        return ticketMapper.toTicketDtoList(tickets);
    }

    public List<TicketDto> getAllExpiredUserTickets(Integer userId) {
        List<Ticket> tickets = ticketRepository.getAllExpiredUserTickets(userId, clock.instant());
        return ticketMapper.toTicketDtoList(tickets);
    }


    public TicketDto getTicketBy(Integer ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new ResourceNotFoundException(TICKET_NOT_FOUND.getMessage())
        );

        return ticketMapper.toTicketDto(ticket);
    }

    public void purchaseTickets(List<TicketPurchaseDto> ticketPurchaseDtos) {
        for (TicketPurchaseDto ticketPurchaseDto : ticketPurchaseDtos) {
            Ticket ticket = new Ticket();
            ticket.setTicketType(getTicketType(ticketPurchaseDto));
            ticket.setSeance(getSeance(ticketPurchaseDto));
            ticket.setSeat(getSeat(ticketPurchaseDto));
            ticket.setUser(getUser(ticketPurchaseDto));
            ticketRepository.save(ticket);
        }
    }

    private User getUser(TicketPurchaseDto ticketPurchaseDto) {
        return userRepository.findById(ticketPurchaseDto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException(USER_NOT_FOUND.getMessage())
        );
    }

    private Seat getSeat(TicketPurchaseDto ticketPurchaseDto) {
        return seatRepository.findByRoomColAndRow(
                ticketPurchaseDto.getSeanceRoomName(),
                ticketPurchaseDto.getSeatCol(),
                ticketPurchaseDto.getSeatRow()).orElseThrow(
                () -> new ResourceNotFoundException(SEAT_NOT_FOUND.getMessage()));
    }

    private Seance getSeance(TicketPurchaseDto ticketPurchaseDto) {
        return seanceRepository.findById(ticketPurchaseDto.getSeanceId()).orElseThrow(
                () -> new ResourceNotFoundException(SEANCE_NOT_FOUND.getMessage())
        );
    }

    private TicketType getTicketType(TicketPurchaseDto ticketPurchaseDto) {
        return ticketTypeRepository.findByName(ticketPurchaseDto.getTicketTypeName()).orElseThrow(
                () -> new ResourceNotFoundException(TICKET_TYPE_NOT_FOUND.getMessage())
        );
    }
}
