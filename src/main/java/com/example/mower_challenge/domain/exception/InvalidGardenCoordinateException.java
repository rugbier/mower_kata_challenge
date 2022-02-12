package com.example.mower_challenge.domain.exception;

public class InvalidGardenCoordinateException extends RuntimeException {
    private static final String CAUSE = "The coordinate for the garden must be positive and greater than zero.";

    public InvalidGardenCoordinateException() {
        super(CAUSE);
    }
}
