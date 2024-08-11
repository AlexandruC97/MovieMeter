package com.itschool.MovieMeter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record ActorDTO(String name,
                       int age,
                       String birthday,
                       String nationality) {
}
