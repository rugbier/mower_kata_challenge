package com.example.mower_challenge.domain;

import com.example.mower_challenge.domain.exception.GardenOutOfBoundsException;
import lombok.Getter;
import lombok.NonNull;
import com.example.mower_challenge.domain.exception.InvalidGardenCoordinateException;
import com.example.mower_challenge.domain.exception.MowerCollisionException;
import com.example.mower_challenge.domain.valueobjects.Coordinate;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Garden {
    private static final int ZERO_COORDINATE = 0;
    private Coordinate bottomCoordinate = new Coordinate(ZERO_COORDINATE, ZERO_COORDINATE);
    @NonNull
    private Coordinate upperCoordinate;
    private List<Coordinate> usedCoordinates = new ArrayList<>();

    public Garden(Coordinate upperCoordinate){
        validateInputCoordinate(upperCoordinate);
        this.upperCoordinate = upperCoordinate;
    }

    private void validateInputCoordinate(Coordinate upperCoordinate) {
        if(upperCoordinate.getX() <= 0 || upperCoordinate.getY() <= 0) throw new InvalidGardenCoordinateException();
    }

    public void verifyCoordinates(final Coordinate coordinate) {
        checkCoordinateInGarden(coordinate);
        checkEmptyCoordinate(coordinate);
    }

    public void useCoordinate(Coordinate coordinate){
        usedCoordinates.add(coordinate);
    }

    private void checkCoordinateInGarden(Coordinate coordinate) {
        if (bottomCoordinate.getX() > coordinate.getX() ||
                bottomCoordinate.getY() > coordinate.getY() ||
                upperCoordinate.getX() < coordinate.getX() ||
                upperCoordinate.getY() < coordinate.getY()) {
            throw new GardenOutOfBoundsException(coordinate);
        }
    }

    private void checkEmptyCoordinate(Coordinate coordinate){
        if(usedCoordinates.stream()
                .anyMatch(c -> c.getX() == coordinate.getX() &&
                        c.getY() == coordinate.getY()))
            throw new MowerCollisionException(coordinate);
    }
}
