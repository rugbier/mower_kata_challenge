package com.example.mower_challenge.infrastructure;

import com.example.mower_challenge.domain.Mower;
import com.example.mower_challenge.domain.repository.MowerRepository;
import com.example.mower_challenge.domain.valueobjects.Coordinate;
import com.example.mower_challenge.domain.valueobjects.CardinalPoint;
import com.example.mower_challenge.infrastructure.repository.MowerOutputRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MowerOutpuRepositoryTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void shouldPrintDataInSystemOutput(){
        Mower mower = new Mower(new Coordinate(0, 0), CardinalPoint.NORTH);
        MowerRepository repository = new MowerOutputRepository();
        repository.save(mower);
        assertEquals("0 0 N\r\n", outContent.toString());
    }
}
