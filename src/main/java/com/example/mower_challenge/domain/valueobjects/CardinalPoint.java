package com.example.mower_challenge.domain.valueobjects;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum CardinalPoint {
    NORTH("N", "W", "E"),
    SOUTH("S", "E", "W"),
    WEST("W", "S", "N"),
    EAST("E", "N", "S");

    private final String code;
    private final String left;
    private final String right;

    CardinalPoint(final String code, final String left, final String right) {
        this.code = code;
        this.left = left;
        this.right = right;
    }

    public CardinalPoint getLeftDirection() {
        return getDirectionFromCode(getLeft());
    }

    public CardinalPoint getRightDirection() {
        return getDirectionFromCode(getRight());
    }

    private static CardinalPoint getDirectionFromCode(final String code) {
        return Stream.of(CardinalPoint.values())
                .filter(d -> d.getCode().equals(code))
                .findAny()
                .orElseThrow(RuntimeException::new);

    }
}
