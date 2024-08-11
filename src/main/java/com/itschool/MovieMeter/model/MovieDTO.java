package com.itschool.MovieMeter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public record MovieDTO(String title,
                       String description,
                       int year,
                       String genre,
                       Set<ActorDTO> actors,
                       @JsonProperty(access = JsonProperty.Access.READ_ONLY) List<ReviewDTO> reviews) {
}
