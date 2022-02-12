package com.example.mower_challenge.application.console.utils;

import com.example.mower_challenge.application.console.exception.InvalidCoordinateInputException;
import com.example.mower_challenge.domain.Garden;
import com.example.mower_challenge.domain.valueobjects.Coordinate;

public class GardenLineReader {

    private GardenLineReader(){

    }
    public static Garden getGarden(String line){
        if(line.split(" ").length != 2) throw new InvalidCoordinateInputException(line);

        try {
            return new Garden(new Coordinate(
                    Integer.parseInt(line.split(" ")[0]),
                    Integer.parseInt(line.split(" ")[1]))
                );
        } catch(NumberFormatException e){
            throw new InvalidCoordinateInputException(line);
        }
    }
}
