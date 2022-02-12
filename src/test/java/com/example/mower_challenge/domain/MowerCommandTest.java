package com.example.mower_challenge.domain;

import com.example.mower_challenge.domain.valueobjects.Coordinate;
import com.example.mower_challenge.domain.valueobjects.CardinalPoint;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.example.mower_challenge.domain.valueobjects.CardinalPoint.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MowerCommandTest {

    @ParameterizedTest
    @MethodSource("argumentsMoveForward")
    void shouldMoveThroughAxisWhenMoveForwardFacingCardinalPoint(int x, int y, CardinalPoint cardinalPoint, Command command,
                                                               int expectedX, int expectedY, CardinalPoint expectedCardinalPoint) {
        Mower mower = new Mower(new Coordinate(x, y), cardinalPoint);
        mower.executeCommand(command);

        assertEquals(expectedY, mower.getCoordinate().getY());
        assertEquals(expectedX, mower.getCoordinate().getX());
        assertEquals(expectedCardinalPoint, mower.getCardinalPoint());
    }

    private static Stream<Arguments> argumentsMoveForward() {
        return Stream.of(
                Arguments.of(0, 0, NORTH, Command.FORWARD, 0, 1, NORTH),
                Arguments.of(0, 0, SOUTH, Command.FORWARD, 0, -1, SOUTH),
                Arguments.of(0, 0, EAST, Command.FORWARD, 1, 0, EAST),
                Arguments.of(0, 0, WEST, Command.FORWARD, -1, 0, WEST)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsMoveLeft")
    void shouldMoveThroughAxisWhenMoveLeftFacingCardinalPoint(int x, int y, CardinalPoint cardinalPoint, Command command,
                                                            int expectedX, int expectedY, CardinalPoint expectedCardinalPoint) {
        Mower mower = new Mower(new Coordinate(x, y), cardinalPoint);
        mower.executeCommand(command);

        assertEquals(expectedY, mower.getCoordinate().getY());
        assertEquals(expectedX, mower.getCoordinate().getX());
        assertEquals(expectedCardinalPoint, mower.getCardinalPoint());
    }

    private static Stream<Arguments> argumentsMoveLeft() {
        return Stream.of(
                Arguments.of(0, 0, NORTH, Command.LEFT, 0, 0, WEST),
                Arguments.of(0, 0, WEST, Command.LEFT, 0, 0, SOUTH),
                Arguments.of(0, 0, SOUTH, Command.LEFT, 0, 0, EAST),
                Arguments.of(0, 0, EAST, Command.LEFT, 0, 0, NORTH)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsMoveRight")
    void shouldMoveThroughAxisWhenMoveRightFacingCardinalPoint(int x, int y, CardinalPoint cardinalPoint, Command command,
                                                             int expectedX, int expectedY, CardinalPoint expectedCardinalPoint) {
        Mower mower = new Mower(new Coordinate(x, y), cardinalPoint);
        mower.executeCommand(command);

        assertEquals(expectedY, mower.getCoordinate().getY());
        assertEquals(expectedX, mower.getCoordinate().getX());
        assertEquals(expectedCardinalPoint, mower.getCardinalPoint());
    }

    private static Stream<Arguments> argumentsMoveRight() {
        return Stream.of(
                Arguments.of(0, 0, NORTH, Command.RIGHT, 0, 0, EAST),
                Arguments.of(0, 0, EAST, Command.RIGHT, 0, 0, SOUTH),
                Arguments.of(0, 0, SOUTH, Command.RIGHT, 0, 0, WEST),
                Arguments.of(0, 0, WEST, Command.RIGHT, 0, 0, NORTH)
        );
    }
}
