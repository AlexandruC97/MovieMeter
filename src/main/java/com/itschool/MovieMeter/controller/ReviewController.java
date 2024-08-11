package com.itschool.MovieMeter.controller;

import com.itschool.MovieMeter.model.MovieDTO;
import com.itschool.MovieMeter.model.ReviewDTO;
import com.itschool.MovieMeter.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Review Manager", description = "Review Manager API that manipulates operations related to reviews")
@RestController
@RequestMapping("reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Post a review on a Movie!")
    @PostMapping("{movieTitle}")
    public MovieDTO postReview(@PathVariable String movieTitle, @RequestBody ReviewDTO review) {
        return reviewService.addReview(movieTitle, review);
    }

    @Operation(summary = "Get movies order by rating")
    @GetMapping("/movies-by-order-rating")
    public List<MovieDTO> getMoviesOrderByRating() {
        return reviewService.getMoviesOrderByRating();
    }

    @Operation(summary = "Delete review by ID!")
    @DeleteMapping("/by-id")
    public boolean deleteReviewById(@RequestParam long id) {
        return reviewService.deleteReviewById(id);
    }

    @Operation(summary = "Update review by ID!")
    @PutMapping("/by-id")
    public ReviewDTO updateReviewById(@RequestParam long id, @RequestBody ReviewDTO review) {
        return reviewService.updateReviewById(id, review);
    }

}
