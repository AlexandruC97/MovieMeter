package com.itschool.MovieMeter.service;


import com.itschool.MovieMeter.entity.Movie;
import com.itschool.MovieMeter.entity.Review;
import com.itschool.MovieMeter.exception.MovieNotFoundException;
import com.itschool.MovieMeter.model.MovieDTO;
import com.itschool.MovieMeter.model.ReviewDTO;
import com.itschool.MovieMeter.repository.MovieRepository;
import com.itschool.MovieMeter.repository.ReviewRepository;
import com.itschool.MovieMeter.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;



    @Test
    void testAddReview_MovieNotFound(){
        String movieTitle = "Movie";

        when(movieRepository.findByTitle(movieTitle)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> reviewServiceImpl.addReview(movieTitle, null));
    }

}
