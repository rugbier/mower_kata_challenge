package com.example.mower_challenge.application;

import com.example.mower_challenge.application.console.exception.InvalidCoordinateInputException;
import com.example.mower_challenge.application.console.exception.InvalidMowerPositionInputException;
import com.example.mower_challenge.application.console.exception.InvalidCardinalPointInputException;
import com.example.mower_challenge.application.console.utils.MowerLineReader;
import com.example.mower_challenge.domain.Mower;
import com.example.mower_challenge.domain.valueobjects.Coordinate;
import com.example.mower_challenge.domain.valueobjects.CardinalPoint;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MowerLineReaderTest {

    @ParameterizedTest
    @MethodSource("argumentsValidDataInput")
    void shouldCreateGardenWithValidCoordinate(String line, Coordinate expectedCoordinate, CardinalPoint expectedCardinalPoint){
        Mower mower = MowerLineReader.getMower(line);

        assertEquals(expectedCoordinate.getX(), mower.getCoordinate().getX());
        assertEquals(expectedCoordinate.getY(), mower.getCoordinate().getY());
        assertEquals(expectedCardinalPoint, mower.getCardinalPoint());
    }

    private static Stream<Arguments> argumentsValidDataInput() {
        return Stream.of(
                Arguments.of("0 0 N", new Coordinate(0, 0), CardinalPoint.NORTH),
                Arguments.of("1 2 S", new Coordinate(1, 2), CardinalPoint.SOUTH),
                Arguments.of("4 5 W", new Coordinate(4, 5), CardinalPoint.WEST),
                Arguments.of("3 2 E", new Coordinate(3, 2), CardinalPoint.EAST)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsInvalidDataInput")
    void shouldThrowExceptionWhenInvalidDataInputGardenWithValidCoordinate(String line, Class<Throwable> expectedClass){
        assertThrows(expectedClass, () -> MowerLineReader.getMower(line));
    }

    private static Stream<Arguments> argumentsInvalidDataInput() {
        return Stream.of(
                Arguments.of("0 0 N S", InvalidMowerPositionInputException.class),
                Arguments.of("A 0 N", InvalidCoordinateInputException.class),
                Arguments.of("0 B N", InvalidCoordinateInputException.class),
                Arguments.of("0 0 T", InvalidCardinalPointInputException.class),
                Arguments.of("0 0 NS", InvalidCardinalPointInputException.class),
                Arguments.of("0 0 RN", InvalidCardinalPointInputException.class)
        );
    }
}
