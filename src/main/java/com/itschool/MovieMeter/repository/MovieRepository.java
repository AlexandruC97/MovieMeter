package com.itschool.MovieMeter.repository;

import com.itschool.MovieMeter.entity.Movie;
import com.itschool.MovieMeter.model.MovieDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<List<Movie>> findAllByGenre(String genre);

    Optional<Movie> findByTitle(String title);

    @Query("SELECT m FROM Movie m LEFT JOIN m.reviews r GROUP BY m ORDER BY AVG(r.rating) DESC")
    List<Movie> findAllMoviesOrderedByAverageRating();
}
