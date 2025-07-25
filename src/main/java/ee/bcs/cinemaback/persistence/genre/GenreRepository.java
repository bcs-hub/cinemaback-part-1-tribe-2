package ee.bcs.cinemaback.persistence.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Query("select (count(g) > 0) from Genre g where g.name = ?1")
    boolean existsBy(String name);

    @Query("select g from Genre g order by g.name")
    List<Genre> findAllAlphabetic();

}
