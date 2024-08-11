package com.itschool.MovieMeter.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public record ReviewDTO(String author,
                        String content,
                        @JsonProperty(access = JsonProperty.Access.READ_ONLY) String date,
                        int rating) {
}
