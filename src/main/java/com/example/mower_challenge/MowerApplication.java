package com.example.mower_challenge;

import com.example.mower_challenge.application.console.MowerFileProcessor;

import java.io.FileNotFoundException;

public class MowerApplication {
    public static void main(final String[] args) throws FileNotFoundException {
        MowerFileProcessor mowerProcessor = new MowerFileProcessor(args[0]);
        mowerProcessor.runMower();
    }
}
