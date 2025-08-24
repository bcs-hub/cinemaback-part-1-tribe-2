package ee.bcs.cinemaback.service.ticket.type;

import ee.bcs.cinemaback.infrastructure.exception.DatabaseConstraintException;
import ee.bcs.cinemaback.infrastructure.exception.DatabaseNameConflictException;
import ee.bcs.cinemaback.infrastructure.exception.ResourceNotFoundException;
import ee.bcs.cinemaback.persistence.ticket.TicketRepository;
import ee.bcs.cinemaback.persistence.ticket.type.TicketType;
import ee.bcs.cinemaback.persistence.ticket.type.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static ee.bcs.cinemaback.infrastructure.Error.*;

@Service
@RequiredArgsConstructor
public class TicketTypeService {

    private final TicketTypeMapper ticketTypeMapper;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;

    public List<TicketTypeDto> getAllTicketTypes() {
        return ticketTypeMapper.toDto(ticketTypeRepository.findAllPriceDescending());
    }

    public void deleteTicketType(int id) {
        if (ticketRepository.existsByTicketTypeId(id)) {
            throw new DatabaseConstraintException(TICKETS_EXIST_WITH_THIS_TICKET_TYPE.getMessage());
        }
        ticketTypeRepository.deleteById(id);
    }

    public void addTicketType(TicketTypeDto dto) {
        if (ticketTypeRepository.existsBy(dto.getName())) {
            throw new DatabaseNameConflictException(TICKET_TYPE_EXISTS.getMessage());
        }
        TicketType ticketType = ticketTypeMapper.toEntity(dto);
        ticketTypeRepository.save(ticketType);
    }

    public void updateTicketType(Integer id, TicketTypeDto dto) {

        TicketType ticketType = ticketTypeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(TICKET_TYPE_NOT_FOUND.getMessage()));

        if (ticketTypeRepository.existsBy(dto.getName()) && !ticketType.getName().equals(dto.getName())) {
            throw new DatabaseNameConflictException(TICKET_TYPE_EXISTS.getMessage());
        }

        ticketType.setName(dto.getName());
        ticketType.setPrice(dto.getPrice());
        ticketTypeRepository.save(ticketType);

    }
}
