package com.itschool.MovieMeter.service.impl;

import com.itschool.MovieMeter.entity.Actor;
import com.itschool.MovieMeter.entity.Movie;
import com.itschool.MovieMeter.entity.Review;
import com.itschool.MovieMeter.exception.ActorNotFoundException;
import com.itschool.MovieMeter.exception.MovieNotFoundException;
import com.itschool.MovieMeter.model.ActorDTO;
import com.itschool.MovieMeter.model.MovieDTO;
import com.itschool.MovieMeter.model.ReviewDTO;
import com.itschool.MovieMeter.repository.ActorRepository;
import com.itschool.MovieMeter.repository.MovieRepository;
import com.itschool.MovieMeter.service.MovieService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    private final ActorRepository actorRepository;

    public MovieServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }


    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) {
        Movie movie = convertToMovie(movieDTO);

        movie.setReviews(List.of());

        Movie savedMovie = movieRepository.save(movie);

        return convertToMovieDTO(savedMovie);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(this::convertToMovieDTO).toList();
    }

    @Override
    public MovieDTO getMovieById(long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie with id: " + id + " not found!"));
        return convertToMovieDTO(movie);
    }

    @Override
    public MovieDTO updateMovie(long id, MovieDTO movieDTO) {
        Movie movie = convertToMovie(movieDTO);
        if(movie.getActors().isEmpty()){
            Movie movieById = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie with id: " + id + " not found!"));
            movie.setActors(movieById.getActors());
        }
        movie.setId(id);
        Movie savedMovie = movieRepository.save(movie);
        return convertToMovieDTO(savedMovie);
    }

    @Override
    public MovieDTO updateMovieByTitle(String title, MovieDTO movieDTO) {

        Optional<Movie> movieOptional = movieRepository.findByTitle(title);

        if(movieOptional.isPresent()){
            Movie movie = movieOptional.get();

            movie.setTitle(movieDTO.title());
            movie.setDescription(movieDTO.description());
            movie.setYear(movieDTO.year());
            movie.setGenre(movieDTO.genre());

            if(!movieDTO.actors().isEmpty()){
                movie.setActors(movieDTO.actors().stream().map(this::convertToActorEntity).collect(Collectors.toSet()));
            }

            Movie savedMovie = movieRepository.save(movie);
            return convertToMovieDTO(savedMovie);
        }
        throw  new MovieNotFoundException("Movie with title: " + title + " not found");
    }

    @Override
    public boolean deleteMovie(long id) {
        movieRepository.deleteById(id);
        return true;
    }

    @Override
    public List<MovieDTO> getAllMoviesByActorName(String actorName) {
        Optional<Actor> actorOptional = actorRepository.findByName(actorName);

        if (actorOptional.isPresent()) {
            Actor actor = actorOptional.get();
            return actor.getMovies().stream()
                    .map(this::convertToMovieDTO)
                    .toList();
        } else {
            throw new ActorNotFoundException("Actor with name: " + actorName + " not found");
        }
    }

    @Override
    public List<MovieDTO> getAllMoviesByGenre(String genre) {
        Optional<List<Movie>> moviesOptional = movieRepository.findAllByGenre(genre);

        if (moviesOptional.isPresent()) {
            List<Movie> movies = moviesOptional.get();
            return movies.stream().map(this::convertToMovieDTO).toList();
        }else {
            throw new MovieNotFoundException("No movies of genre : " + genre + " found!");
        }

    }


    @Override
    public MovieDTO getMovieByTitle(String title) {
        Movie movie = movieRepository.findByTitle(title).orElseThrow(() -> new MovieNotFoundException("Movie with title: " + title + " not found!"));
        return convertToMovieDTO(movie);
    }

    private Movie convertToMovie(MovieDTO movieDTO){
        Movie movie = new Movie();

        movie.setTitle(movieDTO.title());
        movie.setDescription(movieDTO.description());
        movie.setYear(movieDTO.year());
        movie.setGenre(movieDTO.genre());
        movie.setActors(movieDTO.actors().stream()
                .map(this::convertToActorEntity)
                .collect(Collectors.toSet()));


        return movie;
    }

    private MovieDTO convertToMovieDTO(Movie movie) {

        return new MovieDTO(
                movie.getTitle(),
                movie.getDescription(),
                movie.getYear(),
                movie.getGenre(),
                movie.getActors().stream()
                        .map(this::convertToActorDto)
                        .collect(Collectors.toSet()),
                movie.getReviews().stream()
                        .map(this::convertToReviewDTO)
                        .toList()
        );

    }


    private ActorDTO convertToActorDto(Actor actor) {
        return new ActorDTO(actor.getName(),actor.getAge(), actor.getBirthday(), actor.getNationality());
    }

    private Actor convertToActorEntity(ActorDTO actorDTO) {
        Optional<Actor> existingActor = actorRepository.findByName(actorDTO.name());
        if (existingActor.isPresent()) {
            return existingActor.get();
        } else {
            Actor actor = new Actor();
            actor.setName(actorDTO.name());
            actor.setAge(actorDTO.age());
            actor.setBirthday(actorDTO.birthday());
            actor.setNationality(actorDTO.nationality());
            return actorRepository.save(actor);
        }
    }

    private ReviewDTO convertToReviewDTO(Review review) {
        return new ReviewDTO(review.getAuthor(), review.getContent(), review.getDate(), review.getRating());
    }


}
