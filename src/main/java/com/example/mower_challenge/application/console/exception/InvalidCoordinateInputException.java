package com.example.mower_challenge.application.console.exception;


public class InvalidCoordinateInputException extends RuntimeException {
    private static final String CAUSE = "Can not convert input data in Coordinate: %s";

    public InvalidCoordinateInputException(String data) {
        super(String.format(CAUSE, data));
    }
}
