package com.example.mower_challenge.application.console.utils;

import com.example.mower_challenge.application.console.exception.InvalidMowerPositionInputException;
import com.example.mower_challenge.domain.Mower;

public class MowerLineReader {

    private MowerLineReader(){

    }

    public static Mower getMower(String line){
        if(line.split(" ").length != 3) throw new InvalidMowerPositionInputException();

        return new Mower(CoordinateLineReader.getCoordinate(line.substring(0, 3)),
                         CardinalPointLineReader.getCardinalPoint(line));
    }
}
