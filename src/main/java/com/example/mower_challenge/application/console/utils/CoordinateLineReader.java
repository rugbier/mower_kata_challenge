package com.example.mower_challenge.application.console.utils;

import com.example.mower_challenge.application.console.exception.InvalidCoordinateInputException;
import com.example.mower_challenge.domain.valueobjects.Coordinate;

public class CoordinateLineReader {

    private CoordinateLineReader(){

    }

    public static Coordinate getCoordinate(String line){
        if(line.split(" ").length != 2) throw new InvalidCoordinateInputException(line);

        try {
            return new Coordinate(Integer.parseInt(line.split(" ")[0]),
                    Integer.parseInt(line.split(" ")[1]));
        } catch(NumberFormatException e){
            throw new InvalidCoordinateInputException(line);
        }
    }
}
