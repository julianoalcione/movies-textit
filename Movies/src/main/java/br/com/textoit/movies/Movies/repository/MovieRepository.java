package br.com.textoit.movies.Movies.repository;

import br.com.textoit.movies.Movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Query(nativeQuery = true, value  = "select * from movie m where winner order by producers")
    List<Movie> getAllWinners();
}
