package com.example.mower_challenge.domain.service;

import com.example.mower_challenge.domain.Command;
import com.example.mower_challenge.domain.Garden;
import com.example.mower_challenge.domain.Mower;
import com.example.mower_challenge.domain.exception.GardenOutOfBoundsException;
import com.example.mower_challenge.domain.exception.MowerCollisionException;
import com.example.mower_challenge.domain.repository.MowerRepository;
import com.example.mower_challenge.domain.valueobjects.Coordinate;
import com.example.mower_challenge.domain.valueobjects.CardinalPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static com.example.mower_challenge.domain.valueobjects.CardinalPoint.*;

class MowerServiceTest {

    private static final int MAX_X = 5;
    private static final int MAX_Y = 5;

    @Mock
    private MowerRepository mowerRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("argumentsCommandsMowerInsideGarden")
    void shouldMoveThroughGardenWhenCommandExecutionRoutine(Map<Mower, List<Command>> executionPlan, Garden garden,
                                                            Coordinate expectedEndCoordinate, CardinalPoint expectedEndCardinalPoint) {

        MowerService executionRoutine = new MowerServiceSequential(garden, mowerRepository);
        assertDoesNotThrow(() -> executionRoutine.executePlan(executionPlan));

        Mower mower = executionPlan.keySet().stream().findFirst().orElseThrow();

        assertEquals(expectedEndCoordinate.getX(), mower.getCoordinate().getX());
        assertEquals(expectedEndCoordinate.getY(), mower.getCoordinate().getY());
        assertEquals(expectedEndCardinalPoint, mower.getCardinalPoint());

        verify(mowerRepository, times(1)).save(any());
    }

    private static Stream<Arguments> argumentsCommandsMowerInsideGarden() {
        return Stream.of(
                Arguments.of(Map.of(new Mower(new Coordinate(0, 0), NORTH),
                                List.of(Command.FORWARD, Command.RIGHT,
                                        Command.FORWARD, Command.LEFT,
                                        Command.FORWARD, Command.RIGHT,
                                        Command.FORWARD, Command.LEFT,
                                        Command.FORWARD, Command.LEFT,
                                        Command.FORWARD)),
                        new Garden(new Coordinate(MAX_X, MAX_Y)),
                        new Coordinate(1, 3), WEST),

                Arguments.of(Map.of(new Mower(new Coordinate(0, 0), NORTH),
                                List.of(Command.FORWARD, Command.FORWARD,
                                        Command.FORWARD, Command.RIGHT,
                                        Command.FORWARD)),
                        new Garden(new Coordinate(MAX_X, MAX_Y)),
                        new Coordinate(1, 3), EAST),

                Arguments.of(Map.of(new Mower(new Coordinate(0, 0), NORTH),
                                List.of(Command.FORWARD, Command.FORWARD,
                                        Command.FORWARD, Command.FORWARD,
                                        Command.FORWARD)),
                        new Garden(new Coordinate(MAX_X, MAX_Y)),
                        new Coordinate(0, 5), NORTH),

                Arguments.of(Map.of(new Mower(new Coordinate(0, 0), EAST),
                                List.of(Command.FORWARD, Command.FORWARD,
                                        Command.FORWARD, Command.FORWARD,
                                        Command.FORWARD)),
                        new Garden(new Coordinate(MAX_X, MAX_Y)),
                        new Coordinate(5, 0), EAST)
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsCommandsMowerOutsideGarden")
    void shouldThrowExceptionWhenMoveOutsideGardenInRoutineExecution(Map<Mower, List<Command>> executionPlan, Garden garden) {
        MowerService executionRoutine = new MowerServiceSequential(garden, mowerRepository);
        assertThrows(GardenOutOfBoundsException.class, () -> executionRoutine.executePlan(executionPlan));
        verify(mowerRepository, times(0)).save(any());
    }

    private static Stream<Arguments> argumentsCommandsMowerOutsideGarden() {
        return Stream.of(
                Arguments.of(Map.of(new Mower(new Coordinate(0, 0), NORTH),
                                List.of(Command.FORWARD, Command.FORWARD,
                                        Command.FORWARD, Command.LEFT,
                                        Command.FORWARD)),
                        new Garden(new Coordinate(MAX_X, MAX_Y))),

                Arguments.of(Map.of(new Mower(new Coordinate(0, 0), NORTH),
                                List.of(Command.FORWARD, Command.FORWARD,
                                        Command.FORWARD, Command.FORWARD,
                                        Command.FORWARD, Command.FORWARD)),
                        new Garden(new Coordinate(MAX_X, MAX_Y)))
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsCommandsForTwoMowersInsideGardenWithoutCollision")
    void shouldMoveTwoMowersThroughGardenWhenCommandExecutionRoutine(Map<Mower, List<Command>> executionPlan, Garden garden,
                                                                     List<Mower> expectedMowerPositions) {

        MowerService executionRoutine = new MowerServiceSequential(garden, mowerRepository);
        assertDoesNotThrow(() -> executionRoutine.executePlan(executionPlan));

        executionPlan.keySet().forEach(mower -> {
            assertTrue(expectedMowerPositions.stream().anyMatch(m -> m.getCoordinate().getX() == mower.getCoordinate().getX() &&
                            m.getCoordinate().getY() == mower.getCoordinate().getY() &&
                            m.getCardinalPoint() == mower.getCardinalPoint()));
        });

        verify(mowerRepository, times(2)).save(any());
    }

    private static Stream<Arguments> argumentsCommandsForTwoMowersInsideGardenWithoutCollision() {
        return Stream.of(
                Arguments.of(Map.of(new Mower(new Coordinate(0, 0), NORTH), List.of(Command.FORWARD, Command.FORWARD, Command.RIGHT),
                                    new Mower(new Coordinate(1, 1), NORTH), List.of(Command.RIGHT, Command.FORWARD, Command.RIGHT)),
                        new Garden(new Coordinate(MAX_X, MAX_Y)),
                        List.of(new Mower(new Coordinate(0 , 2), EAST),
                               new Mower(new Coordinate(2 , 1), SOUTH)))
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsCommandsForTwoMowersInsideGardenWithCollision")
    void shouldThrowCollisionExceptionWhenCommandExecutionRoutine(Map<Mower, List<Command>> executionPlan, Garden garden) {

        MowerService executionRoutine = new MowerServiceSequential(garden, mowerRepository);
        assertThrows(MowerCollisionException.class, () -> executionRoutine.executePlan(executionPlan));
    }

    private static Stream<Arguments> argumentsCommandsForTwoMowersInsideGardenWithCollision() {
        return Stream.of(
                Arguments.of(Map.of(new Mower(new Coordinate(0, 0), NORTH), List.of(Command.FORWARD, Command.RIGHT, Command.FORWARD, Command.FORWARD),
                                    new Mower(new Coordinate(4, 0), EAST),  List.of(Command.LEFT, Command.FORWARD, Command.LEFT, Command.FORWARD, Command.FORWARD)),
                        new Garden(new Coordinate(MAX_X, MAX_Y)))
        );
    }

}
