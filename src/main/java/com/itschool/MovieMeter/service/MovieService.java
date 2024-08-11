package com.itschool.MovieMeter.service;

import com.itschool.MovieMeter.model.MovieDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {

    MovieDTO addMovie(MovieDTO movieDTO);

    List<MovieDTO> getAllMovies();

    MovieDTO getMovieById(long id);

    MovieDTO updateMovie(long id, MovieDTO movieDTO);

    boolean deleteMovie(long id);

    List<MovieDTO> getAllMoviesByActorName(String actorName);

    List<MovieDTO> getAllMoviesByGenre(String genre);

    MovieDTO updateMovieByTitle(String title, MovieDTO movieDTO);

    MovieDTO getMovieByTitle(String title);

}
