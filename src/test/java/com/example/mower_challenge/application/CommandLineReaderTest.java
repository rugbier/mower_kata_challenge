package com.example.mower_challenge.application;

import com.example.mower_challenge.application.console.utils.CommandLineReader;
import com.example.mower_challenge.domain.Command;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandLineReaderTest {

    @ParameterizedTest
    @MethodSource("argumentsCorrectCommand")
    void shouldCreateCommandsFromLine(String line, List<Command> expectedListCommand){
        List<Command> commands = CommandLineReader.getCommandList(line);

        assertEquals(expectedListCommand, commands);
    }

    private static Stream<Arguments> argumentsCorrectCommand() {
        return Stream.of(
                Arguments.of("MLR", List.of(Command.FORWARD, Command.LEFT, Command.RIGHT)),
                Arguments.of("MMMRRRLLL", List.of(Command.FORWARD, Command.FORWARD, Command.FORWARD,
                        Command.RIGHT, Command.RIGHT, Command.RIGHT,
                        Command.LEFT, Command.LEFT, Command.LEFT)),
                Arguments.of("RLM", List.of(Command.RIGHT, Command.LEFT, Command.FORWARD)),
                Arguments.of("LRM", List.of(Command.LEFT, Command.RIGHT, Command.FORWARD))
        );
    }

    @ParameterizedTest
    @MethodSource("argumentsWrongCommands")
    void shouldThrowExceptionWhenWrongCommandIsPassedFromLine(String line){

        assertThrows(RuntimeException.class,() -> CommandLineReader.getCommandList(line));

    }

    private static Stream<Arguments> argumentsWrongCommands() {
        return Stream.of(
                Arguments.of("ABC"),
                Arguments.of("MMRAR"),
                Arguments.of("LMHG"),
                Arguments.of("MMES")
        );
    }
}
