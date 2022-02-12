package com.example.mower_challenge.application.console.exception;

public class InvalidMowerPositionInputException extends RuntimeException{
    private static final String CAUSE = "The Mower position format data is not valid.";

    public InvalidMowerPositionInputException() {
        super(CAUSE);
    }
}
