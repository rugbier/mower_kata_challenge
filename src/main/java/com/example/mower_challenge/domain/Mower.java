package com.example.mower_challenge.domain;

import com.example.mower_challenge.domain.valueobjects.CardinalPoint;
import lombok.AllArgsConstructor;
import com.example.mower_challenge.domain.valueobjects.Coordinate;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mower {
    private Coordinate coordinate;
    private CardinalPoint cardinalPoint;

    public void moveForward(){
        coordinate.move(cardinalPoint);
    }

    public void turnLeft(){
        cardinalPoint = cardinalPoint.getLeftDirection();
    }

    public void turnRight(){
        cardinalPoint = cardinalPoint.getRightDirection();
    }

    public void executeCommand(Command command) {
        command.execute(this);
    }
}
