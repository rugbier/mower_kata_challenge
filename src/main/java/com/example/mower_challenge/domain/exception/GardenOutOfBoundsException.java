package com.example.mower_challenge.domain.exception;

import com.example.mower_challenge.domain.valueobjects.Coordinate;

public class GardenOutOfBoundsException extends RuntimeException {
    private static final String CAUSE = "The coordinates (%d, %d) are outside";

    public GardenOutOfBoundsException(Coordinate coordinate) {
        super(String.format(CAUSE, coordinate.getX(), coordinate.getY()));
    }
}
