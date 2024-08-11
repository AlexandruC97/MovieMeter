package com.itschool.MovieMeter.service.impl;

import com.itschool.MovieMeter.entity.Actor;
import com.itschool.MovieMeter.entity.Movie;
import com.itschool.MovieMeter.entity.Review;
import com.itschool.MovieMeter.exception.MovieNotFoundException;
import com.itschool.MovieMeter.exception.ReviewNotFoundException;
import com.itschool.MovieMeter.model.ActorDTO;
import com.itschool.MovieMeter.model.MovieDTO;
import com.itschool.MovieMeter.model.ReviewDTO;
import com.itschool.MovieMeter.repository.MovieRepository;
import com.itschool.MovieMeter.repository.ReviewRepository;
import com.itschool.MovieMeter.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieDTO addReview(String movieTitle, ReviewDTO review) {
        Movie movie = movieRepository.findByTitle(movieTitle).orElseThrow(() -> new MovieNotFoundException("Movie with title: " + movieTitle + " not found"));
        Review reviewEntity = convertReviewDTOToReview(review, movie);

        List<Review> reviews;
        if (movie.getReviews().isEmpty()) {
            reviews = List.of(reviewEntity);
        }else{
            reviews = movie.getReviews();
            reviews.add(reviewEntity);
        }
        movie.setReviews(reviews);


        Movie savedMovie = movieRepository.save(movie);
        return convertToMovieDTO(savedMovie);
    }

    @Override
    public List<MovieDTO> getMoviesOrderByRating() {
        List<Movie> movies = movieRepository.findAllMoviesOrderedByAverageRating();
        return movies.stream().map(this::convertToMovieDTO).toList();
    }

    @Override
    public boolean deleteReviewById(long id) {
        reviewRepository.deleteById(id);
        return true;
    }

    @Override
    public ReviewDTO updateReviewById(long id, ReviewDTO review) {
        Optional<Review> optional = reviewRepository.findById(id);
        if (optional.isPresent()) {
            Review reviewEntity = optional.get();
            reviewEntity.setAuthor(review.author());
            reviewEntity.setRating(review.rating());
            reviewEntity.setDate(LocalDate.now().toString());
            reviewEntity.setContent(review.content());

            Review savedReview = reviewRepository.save(reviewEntity);
            return convertToReviewDTO(savedReview);
        }
        throw new ReviewNotFoundException("Review with id: " + id + " not found");
    }

    private Review convertReviewDTOToReview(ReviewDTO reviewDTO, Movie movie) {
        Review review = new Review();
        review.setMovie(movie);
        review.setAuthor(reviewDTO.author());
        review.setContent(reviewDTO.content());
        review.setDate(LocalDate.now().toString());
        review.setRating(reviewDTO.rating());
        return reviewRepository.save(review);
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
                        .distinct().toList()
        );

    }

    private ReviewDTO convertToReviewDTO(Review review) {
        return new ReviewDTO(review.getAuthor(), review.getContent(), review.getDate(), review.getRating());
    }

    private ActorDTO convertToActorDto(Actor actor) {
        return new ActorDTO(actor.getName(),actor.getAge(), actor.getBirthday(), actor.getNationality());
    }
}
