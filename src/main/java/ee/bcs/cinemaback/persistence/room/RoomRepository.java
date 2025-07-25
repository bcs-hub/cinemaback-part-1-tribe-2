package ee.bcs.cinemaback.persistence.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("select (count(r) > 0) from Room r where r.name = ?1")
    boolean existsBy(String name);

    @Query("select r from Room r order by r.name")
    List<Room> findAllAlphabetic();
}
