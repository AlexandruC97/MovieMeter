package com.itschool.MovieMeter.repository;

import com.itschool.MovieMeter.entity.Movie;
import com.itschool.MovieMeter.entity.Review;
import com.itschool.MovieMeter.model.MovieDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
