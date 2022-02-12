package com.example.mower_challenge.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Consumer;


@AllArgsConstructor
@Getter
public class Coordinate {
    private static final Map<CardinalPoint, Consumer<Coordinate>> MOVEMENTS = Map.of(
            CardinalPoint.NORTH, Coordinate::forward,
            CardinalPoint.EAST, Coordinate::right,
            CardinalPoint.SOUTH, Coordinate::backward,
            CardinalPoint.WEST, Coordinate::left
    );
    private int x;
    private int y;

    public void move(CardinalPoint cardinalPoint){
        MOVEMENTS.get(cardinalPoint).accept(this);
    }

    private void forward() {
        y += 1;
    }

    private void backward() {
        y -= 1;
    }

    private void right() {
        x +=1;
    }

    private void left() {
        x -= 1;
    }
}
