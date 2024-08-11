package com.itschool.MovieMeter.service;

import com.itschool.MovieMeter.exception.ActorNotFoundException;
import com.itschool.MovieMeter.exception.MovieNotFoundException;
import com.itschool.MovieMeter.repository.ActorRepository;
import com.itschool.MovieMeter.repository.MovieRepository;
import com.itschool.MovieMeter.service.impl.ActorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ActorServiceImpl actorServiceImpl;

    @Test
    void testGetActorById_ActorNotFound() {
        long actorId = 10;
        when(actorRepository.findById(actorId)).thenReturn(Optional.empty());
        assertThrows(ActorNotFoundException.class, () -> actorServiceImpl.getActorById(actorId));
    }

    @Test
    void testGetAllActorsByMovie_MovieNotFound() {
        String movie = "Ben 10";
        when(movieRepository.findByTitle(movie)).thenReturn(Optional.empty());
        assertThrows(MovieNotFoundException.class, () -> actorServiceImpl.getAllActorsByMovie(movie));
    }
}
