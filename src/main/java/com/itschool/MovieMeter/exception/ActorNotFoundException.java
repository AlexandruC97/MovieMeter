package com.itschool.MovieMeter.exception;

public class ActorNotFoundException extends RuntimeException{

    public ActorNotFoundException(String message) {
        super(message);
    }
}
