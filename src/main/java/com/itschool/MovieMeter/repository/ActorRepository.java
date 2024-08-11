package com.itschool.MovieMeter.repository;

import com.itschool.MovieMeter.entity.Actor;
import com.itschool.MovieMeter.model.ActorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    Optional<Actor> findByName(String name);
    Optional<List<Actor>> findAllByNationality(String nationality);
}
