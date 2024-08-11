package com.itschool.MovieMeter.controller;

import com.itschool.MovieMeter.model.MovieDTO;
import com.itschool.MovieMeter.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Movie Manager", description = "Movie Manager API that manipulates operations related to movies!")
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @Operation(summary = "Add a new movie!", description = "Add a new movie to the database along with their list of actors!")
    @PostMapping
    public MovieDTO insertUser(@RequestBody MovieDTO movieDTO) { // @RequestBody annotation needed because the request body is a JSON object that holds another JSON object
        return movieService.addMovie(movieDTO);
    }

    @Operation(summary = "Get all movies based on Actor name!")
    @GetMapping("/by-actor")
    public List<MovieDTO> getAllMoviesByActorName(@RequestParam String actorName) {
        return movieService.getAllMoviesByActorName(actorName);
    }

    @Operation(summary = "Get all movies!")
    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @Operation(summary = "Get movie by ID!")
    @GetMapping("/{id}")
    public MovieDTO getMovieById(@PathVariable long id) {
        return movieService.getMovieById(id);
    }

    @Operation(summary = "Delete a movie by ID!")
    @DeleteMapping("/{id}")
    public boolean deleteMovieById(@PathVariable long id) {
        return movieService.deleteMovie(id);
    }

    @Operation(summary = "Update a movie by ID! Let the array of Actors empty if you want to keep the original list!")
    @PutMapping("/{id}")
    public MovieDTO updateMovieById(@PathVariable long id, @RequestBody MovieDTO movieDTO) {
        return movieService.updateMovie(id, movieDTO);
    }

    @Operation(summary = "Update a movie by Title! Let the array of Actors empty if you want to keep the original list!")
    @PutMapping("/by-title/{title}")
    public MovieDTO updateMovieByTitle(@PathVariable String title, @RequestBody MovieDTO movieDTO) {
        return movieService.updateMovieByTitle(title, movieDTO);
    }

    @Operation(summary = "Get movie by title!")
    @GetMapping("/by-title")
    public MovieDTO getMovieByTitle(@RequestParam String title) {
        return movieService.getMovieByTitle(title);
    }

    @Operation(summary = "Get all movies by genre!")
    @GetMapping("/by-genre")
    public List<MovieDTO> getAllMoviesByGenre(@RequestParam String genre) {
        return movieService.getAllMoviesByGenre(genre);
    }

}
