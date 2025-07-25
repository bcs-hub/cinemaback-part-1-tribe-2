package ee.bcs.cinemaback.persistence.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("select t from Ticket t where t.seance.id = ?1 order by t.seat.row, t.seat.col")
    List<Ticket> findSeanceTickets(Integer seanceId);

    @Query("select t from Ticket t where t.user.id = ?1 and t.seance.startTime <= ?2 order by t.seance.startTime")
    List<Ticket> getAllExpiredUserTickets(Integer id, Instant startTime);

    @Query("select t from Ticket t where t.user.id = ?1 and t.seance.startTime >= ?2 order by t.seance.startTime")
    List<Ticket> getAllActiveUserTickets(Integer id, Instant startTime);

    @Query("select count(t) from Ticket t where t.seance.id = ?1")
    Integer countBySeance(Integer id);

    @Query("select (count(t) > 0) from Ticket t where t.ticketType.id = ?1")
    boolean existsByTicketTypeId(Integer id);

    @Query("select (count(t) > 0) from Ticket t where t.seance.id = ?1")
    boolean existsBySeanceId(Integer id);

    @Query("select count(t) from Ticket t where t.user.id = ?1")
    Integer countByUserId(Integer id);
}
