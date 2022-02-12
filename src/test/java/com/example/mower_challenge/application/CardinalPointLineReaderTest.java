package com.example.mower_challenge.application;

import com.example.mower_challenge.application.console.exception.InvalidMowerPositionInputException;
import com.example.mower_challenge.application.console.exception.InvalidCardinalPointInputException;
import com.example.mower_challenge.application.console.utils.CardinalPointLineReader;
import com.example.mower_challenge.domain.valueobjects.CardinalPoint;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CardinalPointLineReaderTest {

    @ParameterizedTest
    @MethodSource("argumentsValidCardinalPointDataInput")
    void shouldCreateGardenWithValidCoordinate(String line, CardinalPoint expectedCardinalPoint){
        CardinalPoint cardinalPoint = CardinalPointLineReader.getCardinalPoint(line);

        assertEquals(expectedCardinalPoint, cardinalPoint);
    }

    private static Stream<Arguments> argumentsValidCardinalPointDataInput() {
        return Stream.of(
                Arguments.of("0 0 N", CardinalPoint.NORTH),
                Arguments.of("0 0 S", CardinalPoint.SOUTH),
                Arguments.of("0 0 W", CardinalPoint.WEST),
                Arguments.of("0 0 E", CardinalPoint.EAST)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsInvalidCardinalPointDataInput")
    void shouldCreateGardenWithValidCoordinate(String line, Class<Throwable> expectedClass){
        assertThrows(expectedClass, () -> CardinalPointLineReader.getCardinalPoint(line));
    }

    private static Stream<Arguments> argumentsInvalidCardinalPointDataInput() {
        return Stream.of(
                Arguments.of("0 0 N T", InvalidMowerPositionInputException.class),
                Arguments.of("0 0 Q", InvalidCardinalPointInputException.class),
                Arguments.of("0 0 A", InvalidCardinalPointInputException.class),
                Arguments.of("0 0 NN", InvalidCardinalPointInputException.class)
        );
    }
}
