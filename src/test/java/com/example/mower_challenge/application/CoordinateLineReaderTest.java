package com.example.mower_challenge.application;

import com.example.mower_challenge.application.console.exception.InvalidCoordinateInputException;
import com.example.mower_challenge.application.console.utils.CoordinateLineReader;
import com.example.mower_challenge.domain.valueobjects.Coordinate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoordinateLineReaderTest {

    @ParameterizedTest
    @MethodSource("argumentsValidCoordinate")
    void shouldCreateCoordinateFromLine(String line, Coordinate expectedCoordinate){
        Coordinate coordinate = CoordinateLineReader.getCoordinate(line);

        assertEquals(expectedCoordinate.getX(), coordinate.getX());
        assertEquals(expectedCoordinate.getY(), coordinate.getY());
    }

    private static Stream<Arguments> argumentsValidCoordinate() {
        return Stream.of(
                Arguments.of("1 11", new Coordinate(1, 11)),
                Arguments.of("2 1", new Coordinate(2, 1)),
                Arguments.of("0 0", new Coordinate(0, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsInvalidCoordinate")
    void shouldThrowExceptionWhenInvalidDataInput(String line){
        assertThrows(InvalidCoordinateInputException.class, () -> CoordinateLineReader.getCoordinate(line));

    }

    private static Stream<Arguments> argumentsInvalidCoordinate() {
        return Stream.of(
                Arguments.of("1 A"),
                Arguments.of("B 1"),
                Arguments.of("B B"),
                Arguments.of("BB"),
                Arguments.of("A B C")
        );
    }


}
