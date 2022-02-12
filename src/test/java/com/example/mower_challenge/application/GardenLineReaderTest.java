package com.example.mower_challenge.application;

import com.example.mower_challenge.application.console.exception.InvalidCoordinateInputException;
import com.example.mower_challenge.application.console.utils.GardenLineReader;
import com.example.mower_challenge.domain.Garden;
import com.example.mower_challenge.domain.exception.InvalidGardenCoordinateException;
import com.example.mower_challenge.domain.valueobjects.Coordinate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GardenLineReaderTest {

    @ParameterizedTest
    @MethodSource("argumentsValidGardenLimit")
    void shouldCreateGardenWithValidCoordinate(String line, Coordinate expectedBottomCoordinate, Coordinate expectedUpperCoordinate){
        Garden garden = GardenLineReader.getGarden(line);

        assertEquals(expectedBottomCoordinate.getX(), garden.getBottomCoordinate().getX());
        assertEquals(expectedBottomCoordinate.getY(), garden.getBottomCoordinate().getY());
        assertEquals(expectedUpperCoordinate.getX(), garden.getUpperCoordinate().getX());
        assertEquals(expectedUpperCoordinate.getY(), garden.getUpperCoordinate().getY());
    }

    private static Stream<Arguments> argumentsValidGardenLimit() {
        return Stream.of(
                Arguments.of("3 3", new Coordinate(0, 0), new Coordinate(3, 3)),
                Arguments.of("2 3", new Coordinate(0, 0), new Coordinate(2, 3)),
                Arguments.of("5 6", new Coordinate(0, 0), new Coordinate(5, 6))
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsInvalidGardenLimit")
    void shouldThrowExceptionWhenInvalidDataInput(String line){
        assertThrows(InvalidCoordinateInputException.class, () -> GardenLineReader.getGarden(line));
    }

    private static Stream<Arguments> argumentsInvalidGardenLimit() {
        return Stream.of(
                Arguments.of("1 A"),
                Arguments.of("B 1"),
                Arguments.of("B B"),
                Arguments.of("BB"),
                Arguments.of("A B C")
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsInvalidGardenUpperCoordinate")
    void shouldThrowExceptionWhenInvalidCoordinate(String line){
        assertThrows(InvalidGardenCoordinateException.class, () -> GardenLineReader.getGarden(line));
    }

    private static Stream<Arguments> argumentsInvalidGardenUpperCoordinate() {
        return Stream.of(
                Arguments.of("0 0"),
                Arguments.of("0 -1"),
                Arguments.of("-1 0"),
                Arguments.of("-1 -1")
        );
    }
}
