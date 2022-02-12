package com.example.mower_challenge.domain;

import java.util.function.Consumer;

public enum Command {

    FORWARD("M", Mower::moveForward),
    RIGHT("R", Mower::turnRight),
    LEFT("L", Mower::turnLeft);

    private String code;
    private Consumer<Mower> action;

    Command(String code, Consumer<Mower> action) {
        this.code = code;
        this.action = action;
    }

    public void execute(Mower mower) {
        action.accept(mower);
    }
}
