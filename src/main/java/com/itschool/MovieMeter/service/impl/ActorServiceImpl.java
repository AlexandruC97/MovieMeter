package com.itschool.MovieMeter.service.impl;

import com.itschool.MovieMeter.entity.Actor;
import com.itschool.MovieMeter.entity.Movie;
import com.itschool.MovieMeter.exception.ActorNotFoundException;
import com.itschool.MovieMeter.exception.MovieNotFoundException;
import com.itschool.MovieMeter.model.ActorDTO;
import com.itschool.MovieMeter.repository.ActorRepository;
import com.itschool.MovieMeter.repository.MovieRepository;
import com.itschool.MovieMeter.service.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;

    public ActorServiceImpl(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }


    @Override
    public List<ActorDTO> getAllActors() {
        List<Actor> actors = actorRepository.findAll();
        return actors.stream().map(this::convertToActorDto).toList();
    }

    @Override
    public ActorDTO getActorByName(String name) {
        Optional<Actor> actor = actorRepository.findByName(name);
        if (actor.isPresent()) {
            return convertToActorDto(actor.get());
        }
        throw new ActorNotFoundException("Actor with name: " + name + " not found");
    }

    @Override
    public List<ActorDTO> getAllActorsByMovie(String movie) {
        Optional<Movie> movieOptional = movieRepository.findByTitle(movie);
        if (movieOptional.isPresent()) {
            Movie movieEntity = movieOptional.get();
            return movieEntity.getActors().stream().map(this::convertToActorDto).toList();
        }
        throw  new MovieNotFoundException("Movie with title: " + movie + " not found");
    }

    @Override
    public List<ActorDTO> getAllActorsByNationality(String nationality) {
        Optional<List<Actor>> actorsEntities = actorRepository.findAllByNationality(nationality);
        if (actorsEntities.isPresent()) {
            return actorsEntities.get().stream().map(this::convertToActorDto).toList();
        }
        throw new ActorNotFoundException("Actors with nationality : " + nationality + " not found");
    }

    @Override
    public ActorDTO updateActor(String name, ActorDTO actorDTO) {

        Optional<Actor> actorOptional = actorRepository.findByName(name);

        if (actorOptional.isPresent()) {
            Actor actorEntity = actorOptional.get();

            actorEntity.setName(actorDTO.name());
            actorEntity.setAge(actorDTO.age());
            actorEntity.setBirthday(actorDTO.birthday());
            actorEntity.setNationality(actorDTO.nationality());

            Actor savedActor = actorRepository.save(actorEntity);

            return convertToActorDto(savedActor);
        }
        throw new ActorNotFoundException("Actor with name: " + name + " not found");

    }

    @Override
    public boolean deleteActor(long id) {
        actorRepository.deleteById(id);
        return true;
    }

    @Override
    public ActorDTO getActorById(long id) {
        Actor actor = actorRepository.findById(id).orElseThrow(() -> new ActorNotFoundException( "Actor with id: " + id + " not found"));
        return convertToActorDto(actor);
    }


    private ActorDTO convertToActorDto(Actor actor) {
        return new ActorDTO(actor.getName(), actor.getAge(), actor.getBirthday(), actor.getNationality());
    }

}
