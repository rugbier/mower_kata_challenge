package com.example.mower_challenge.domain;

import com.example.mower_challenge.domain.exception.GardenOutOfBoundsException;
import com.example.mower_challenge.domain.exception.MowerCollisionException;
import com.example.mower_challenge.domain.valueobjects.Coordinate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GardenTest {
    private static final int MAX_X = 5;
    private static final int MAX_Y = 5;


    @ParameterizedTest
    @MethodSource("argumentsCoordinateInsideGarden")
    void shouldNotThrowExceptionWhenCoordinateIsInsideGarden(int x, int y, int max_X, int max_Y) {
        Garden garden = new Garden(new Coordinate(max_X, max_Y));

        assertDoesNotThrow(() -> garden.verifyCoordinates(new Coordinate(x, y)));
    }

    private static Stream<Arguments> argumentsCoordinateInsideGarden() {
        return Stream.of(
                Arguments.of(1, 1, MAX_X, MAX_Y),
                Arguments.of(1, 2, MAX_X, MAX_Y),
                Arguments.of(0, 0, MAX_X, MAX_Y),
                Arguments.of(MAX_X, MAX_Y, MAX_X, MAX_Y)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsCoordinateOutsideGarden")
    void shouldThrowExceptionWhenCoordinateIsOutsideGarden(int x, int y, int max_X, int max_Y) {
        Garden garden = new Garden(new Coordinate(max_X, max_Y));

        assertThrows(GardenOutOfBoundsException.class, () -> garden.verifyCoordinates(new Coordinate(x, y)));
    }

    private static Stream<Arguments> argumentsCoordinateOutsideGarden() {
        return Stream.of(
                Arguments.of(10, 10, MAX_X, MAX_Y),
                Arguments.of(6, 6, MAX_X, MAX_Y)
        );
    }

    @Test
    void shouldNotThrowExceptionWhenOccupeCoordinateInEmptyGarden(){
        Coordinate newCoordinate = new Coordinate(1, 1);
        Coordinate maxCoordinate = new Coordinate(MAX_X, MAX_Y);
        Garden garden = new Garden(maxCoordinate);

        assertDoesNotThrow(() -> garden.verifyCoordinates(newCoordinate));
    }


    @ParameterizedTest
    @MethodSource("argumentsCoordinateNotOccupiedInGarden")
    void shouldNotThrowExceptionWhenCoordinateIsNotOccupied(Coordinate newCoordinate, Coordinate existingCoordinate,
                                                            Coordinate maxCoordinate) {
        Garden garden = new Garden(maxCoordinate);
        garden.useCoordinate(existingCoordinate);

        assertDoesNotThrow(() -> garden.verifyCoordinates(newCoordinate));
    }

    private static Stream<Arguments> argumentsCoordinateNotOccupiedInGarden() {
        return Stream.of(
                Arguments.of(new Coordinate(1, 1), new Coordinate(3, 4), new Coordinate(MAX_X, MAX_Y)),
                Arguments.of(new Coordinate(2, 2), new Coordinate(4, 3), new Coordinate(MAX_X, MAX_Y))
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsCoordinateOccupiedInGarden")
    void shouldThrowExceptionWhenCoordinateIsOccupied(Coordinate newCoordinate, Coordinate existingCoordinate,
                                                            Coordinate maxCoordinate) {
        Garden garden = new Garden(maxCoordinate);
        garden.useCoordinate(existingCoordinate);

        assertThrows(MowerCollisionException.class, () -> garden.verifyCoordinates(newCoordinate));
    }

    private static Stream<Arguments> argumentsCoordinateOccupiedInGarden() {
        return Stream.of(
                Arguments.of(new Coordinate(1, 1), new Coordinate(1, 1), new Coordinate(MAX_X, MAX_Y)),
                Arguments.of(new Coordinate(2, 2), new Coordinate(2, 2), new Coordinate(MAX_X, MAX_Y))
        );
    }

}
