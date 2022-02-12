package com.example.mower_challenge.domain.exception;

import com.example.mower_challenge.domain.valueobjects.Coordinate;

public class MowerCollisionException extends RuntimeException {
    private static final String CAUSE = "The coordinate (%d, %d) is not empty";

    public MowerCollisionException(Coordinate coordinate) {
        super(String.format(CAUSE, coordinate.getX(), coordinate.getY()));
    }
}
