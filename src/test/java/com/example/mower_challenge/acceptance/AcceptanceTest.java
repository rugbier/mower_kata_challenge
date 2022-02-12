package com.example.mower_challenge.acceptance;

import com.example.mower_challenge.application.console.MowerFileProcessor;
import com.example.mower_challenge.application.console.exception.InvalidCoordinateInputException;
import com.example.mower_challenge.domain.exception.GardenOutOfBoundsException;
import com.example.mower_challenge.domain.exception.InvalidGardenCoordinateException;
import com.example.mower_challenge.domain.exception.MowerCollisionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AcceptanceTest {
    private static final String TEST_FILE_NAME = "a_test_file.txt";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        File file = new File(TEST_FILE_NAME);
        file.delete();
    }

    @ParameterizedTest
    @MethodSource("argumentsMowersInputData")
    void shouldFinishRoutineCorrectly(List<String> mowersInputData, List<String> expectedResult) throws FileNotFoundException, UnsupportedEncodingException {

        createTextFile(TEST_FILE_NAME, mowersInputData);

        MowerFileProcessor mowerProcessor = new MowerFileProcessor(TEST_FILE_NAME);
        mowerProcessor.runMower();

        List<String> result = convertStringToList(outContent.toString());

        assertEquals(expectedResult, result);
    }

    private static Stream<Arguments> argumentsMowersInputData() {
        return Stream.of(
                Arguments.of(List.of("5 5",
                                     "1 2 N", "LMLMLMLMM",
                                     "3 3 E", "MMRMMRMRRM"),
                            List.of("1 3 N",
                                    "5 1 E"))
        );
    }


    @ParameterizedTest
    @MethodSource("argumentsMowersBadRoutineInputData")
    void shouldThrowExceptionWhenExecuteRoutine(List<String> mowersInputData,
                                                Class<Throwable> expectedExceptionClass) throws FileNotFoundException, UnsupportedEncodingException {
        String fileName = "a_test_file.txt";
        createTextFile(fileName, mowersInputData);

        assertThrows(expectedExceptionClass, () -> {
            MowerFileProcessor mowerProcessor = new MowerFileProcessor(fileName);
            mowerProcessor.runMower();
        });
    }

    private static Stream<Arguments> argumentsMowersBadRoutineInputData() {
        return Stream.of(
                Arguments.of(List.of("5 5", "0 0 N", "MRMM", "4 0 E", "LMLMM"), MowerCollisionException.class),
                Arguments.of(List.of("5 5", "0 0 N", "MLM"), GardenOutOfBoundsException.class),
                Arguments.of(List.of("5 -5", "0 0 N", "MLM"), InvalidGardenCoordinateException.class),
                Arguments.of(List.of("5 5", "-1 0 N", "MLM"), InvalidCoordinateInputException.class)
        );
    }



    private void createTextFile(String name, List<String> lines) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(name, "UTF-8");
        lines.forEach(l -> writer.println(l));
        writer.close();
    }

    private List<String> convertStringToList(String output){
        return Arrays.stream(output.split("\\r?\\n")).collect(Collectors.toList());
    }
}
