package com.itschool.MovieMeter.service;

import com.itschool.MovieMeter.model.MovieDTO;
import com.itschool.MovieMeter.model.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    MovieDTO addReview(String movieTitle, ReviewDTO review);
    List<MovieDTO> getMoviesOrderByRating();
    boolean deleteReviewById(long id);
    ReviewDTO updateReviewById(long id, ReviewDTO review);
}
