package com.example.mower_challenge.domain;

import com.example.mower_challenge.domain.valueobjects.Coordinate;
import com.example.mower_challenge.domain.valueobjects.CardinalPoint;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.example.mower_challenge.domain.valueobjects.CardinalPoint.*;

class MowerTest {


    @ParameterizedTest
    @MethodSource("argumentsMoveForward")
    void shouldMoveThroughAxisWhenMoveForwardFacingCardinalPoint(int x, int y, CardinalPoint cardinalPoint,
                                                               int expectedX, int expectedY, CardinalPoint expectedCardinalPoint) {
        Mower mower = new Mower(new Coordinate(x, y), cardinalPoint);
        mower.moveForward();

        assertEquals(expectedY, mower.getCoordinate().getY());
        assertEquals(expectedX, mower.getCoordinate().getX());
        assertEquals(expectedCardinalPoint, mower.getCardinalPoint());
    }

    private static Stream<Arguments> argumentsMoveForward() {
        return Stream.of(
                Arguments.of(0, 0, NORTH, 0, 1, NORTH),
                Arguments.of(0, 0, SOUTH, 0, -1, SOUTH),
                Arguments.of(0, 0, EAST, 1, 0, EAST),
                Arguments.of(0, 0, WEST, -1, 0, WEST)
        );
    }


    @ParameterizedTest
    @MethodSource("argumentsTurnRight")
    void shouldMoveThroughAxisWhenTurnRightAndMoveForwardFacingCardinalPoint(int x, int y, CardinalPoint cardinalPoint,
                                                                           int expectedX, int expectedY, CardinalPoint expectedCardinalPoint) {
        Mower mower = new Mower(new Coordinate(x, y), cardinalPoint);
        mower.turnRight();
        mower.moveForward();


        assertEquals(expectedY, mower.getCoordinate().getY());
        assertEquals(expectedX, mower.getCoordinate().getX());
        assertEquals(expectedCardinalPoint, mower.getCardinalPoint());
    }

    private static Stream<Arguments> argumentsTurnRight() {
        return Stream.of(
                Arguments.of(0, 0, NORTH, 1, 0, EAST),
                Arguments.of(0, 0, EAST, 0, -1, SOUTH),
                Arguments.of(0, 0, SOUTH, -1, 0, WEST),
                Arguments.of(0, 0, WEST, 0, 1, NORTH)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsTurnLeft")
    void shouldMoveThroughAxisWhenTurnLeftAndMoveForwardFacingCardinalPoint(int x, int y, CardinalPoint cardinalPoint,
                                                                           int expectedX, int expectedY, CardinalPoint expectedCardinalPoint) {
        Mower mower = new Mower(new Coordinate(x, y), cardinalPoint);
        mower.turnLeft();
        mower.moveForward();


        assertEquals(expectedY, mower.getCoordinate().getY());
        assertEquals(expectedX, mower.getCoordinate().getX());
        assertEquals(expectedCardinalPoint, mower.getCardinalPoint());
    }

    private static Stream<Arguments> argumentsTurnLeft() {
        return Stream.of(
                Arguments.of(0, 0, NORTH, -1, 0, WEST),
                Arguments.of(0, 0, WEST, 0, -1, SOUTH),
                Arguments.of(0, 0, SOUTH, 1, 0, EAST),
                Arguments.of(0, 0, EAST, 0, 1, NORTH)
        );
    }

}
