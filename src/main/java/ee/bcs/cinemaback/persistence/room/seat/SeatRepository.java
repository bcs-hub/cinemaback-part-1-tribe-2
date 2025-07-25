package ee.bcs.cinemaback.persistence.room.seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    @Query("select s from Seat s where s.room.name = ?1 and s.col = ?2 and s.row = ?3")
    Optional<Seat> findByRoomColAndRow(String name, Integer col, Integer row);


}
