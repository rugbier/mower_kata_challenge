package com.example.mower_challenge.application.console.exception;

public class InvalidCardinalPointInputException extends RuntimeException {
    private static final String CAUSE = "Can not convert input data in valid CardinalPoint: %s";

    public InvalidCardinalPointInputException(String data) {
        super(String.format(CAUSE, data));
    }
}
