package com.example.mower_challenge.application.console.utils;

import com.example.mower_challenge.application.console.exception.InvalidMowerPositionInputException;
import com.example.mower_challenge.application.console.exception.InvalidCardinalPointInputException;
import com.example.mower_challenge.domain.valueobjects.CardinalPoint;

public class CardinalPointLineReader {

    private CardinalPointLineReader(){

    }

    public static CardinalPoint getCardinalPoint(String line){
        if(line.split(" ").length != 3) throw new InvalidMowerPositionInputException();

        switch (line.split(" ")[2]){
            case "N":
                return CardinalPoint.NORTH;
            case "S":
                return CardinalPoint.SOUTH;
            case "E":
                return CardinalPoint.EAST;
            case "W":
                return CardinalPoint.WEST;
            default:
                throw new InvalidCardinalPointInputException(line);

        }
    }
}
