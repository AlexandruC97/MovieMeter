package com.itschool.MovieMeter.service;

import com.itschool.MovieMeter.exception.ActorNotFoundException;
import com.itschool.MovieMeter.exception.MovieNotFoundException;
import com.itschool.MovieMeter.repository.ActorRepository;
import com.itschool.MovieMeter.repository.MovieRepository;
import com.itschool.MovieMeter.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private MovieServiceImpl movieServiceImpl;

    @Test
    void testGetMovieByTitle_MovieNotFound() {
        String movieTitle = "Jupiter Ascension";
        when(movieRepository.findByTitle(movieTitle)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieServiceImpl.getMovieByTitle(movieTitle));
    }

    @Test
    void testGetAllMoviesByActorName_MoviesNotFound(){
        String actorName = "Manole";
        when(actorRepository.findByName(actorName)).thenReturn(Optional.empty());
        assertThrows(ActorNotFoundException.class, () -> movieServiceImpl.getAllMoviesByActorName(actorName));
    }

}
