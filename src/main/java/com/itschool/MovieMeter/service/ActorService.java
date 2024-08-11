package com.itschool.MovieMeter.service;

import com.itschool.MovieMeter.model.ActorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActorService {
    List<ActorDTO> getAllActors();
    ActorDTO getActorByName(String name);
    List<ActorDTO> getAllActorsByMovie(String movie);
    List<ActorDTO> getAllActorsByNationality(String nationality);
    ActorDTO updateActor(String name, ActorDTO actorDTO);
    boolean deleteActor(long id);
    ActorDTO getActorById(long id);
}
