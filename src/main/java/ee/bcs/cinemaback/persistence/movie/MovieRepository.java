package ee.bcs.cinemaback.persistence.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("select (count(m) > 0) from Movie m where m.title = ?1")
    boolean existsBy(String title);
    @Query("select (count(m) > 0) from Movie m where m.title = ?1 and m.status = 'D'")
    boolean deletedByTitle(String title);

    @Query("select m from Movie m where m.status = 'A' order by m.title")
    List<Movie> findAllActiveMovies();

    @Query("select m from Movie m where m.status = ?1 order by m.title")
    List<Movie> findAllMoviesBy(String status);

    @Query("select (count(m) > 0) from Movie m where m.genre.id = ?1")
    boolean existsByGenre(Integer id);

    @Query("select id from Movie where title = ?1")
    Integer getIdByTitle(String title);

}
