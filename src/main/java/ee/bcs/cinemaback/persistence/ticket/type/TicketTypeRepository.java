package ee.bcs.cinemaback.persistence.ticket.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TicketTypeRepository extends JpaRepository<TicketType, Integer> {
    @Query("select (count(t) > 0) from TicketType t where t.name = ?1")
    boolean existsBy(String name);

    @Query("select t from TicketType t order by t.price desc")
    List<TicketType> findAllPriceDescending();

    @Query("select t from TicketType t where t.name = ?1")
    Optional <TicketType> findByName(String ticketTypeName);

    @Query("select (count(t) > 0) from TicketType t where lower(t.name) = lower(?1)")
    boolean existsByNameCI(String name);
}
