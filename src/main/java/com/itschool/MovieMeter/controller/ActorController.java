package com.itschool.MovieMeter.controller;

import com.itschool.MovieMeter.model.ActorDTO;
import com.itschool.MovieMeter.service.ActorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Actor Manager", description = "Actor Manager API that manipulates operations related to actors")
@RestController
@RequestMapping("actors")
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @Operation(summary = "Get all actors!")
    @GetMapping
    public List<ActorDTO> getAllActors() {
        return actorService.getAllActors();
    }

    @Operation(summary = "Get actor by name!")
    @GetMapping("/by-name")
    public ActorDTO getActorByName(@RequestParam String actorName) {
        return actorService.getActorByName(actorName);
    }

    @Operation(summary = "Get all actors by Movie title!")
    @GetMapping("/by-movie-title")
    public List<ActorDTO> getAllActorsByMovieTitle(@RequestParam String movieTitle) {
        return actorService.getAllActorsByMovie(movieTitle);
    }

    @Operation(summary = "Get all actors by nationality!")
    @GetMapping("/by-nationality")
    public List<ActorDTO> getAllActorsByNationality(@RequestParam String nationality) {
        return actorService.getAllActorsByNationality(nationality);
    }

    @Operation(summary = "Update an actor by name!")
    @PutMapping("/by-name/{name}")
    public ActorDTO updateActorByName(@PathVariable String name, @RequestBody ActorDTO actorDTO) {
        return actorService.updateActor(name, actorDTO);
    }

    @Operation(summary = "Delete actor by id!")
    @DeleteMapping("/by-id/{id}")
    public void deleteActorById(@PathVariable long id) {
        actorService.deleteActor(id);
    }

    @Operation(summary = "Get actor by ID!")
    @GetMapping("/by-id/{id}")
    public ActorDTO getActorById(@PathVariable long id) {
        return actorService.getActorById(id);
    }
}
