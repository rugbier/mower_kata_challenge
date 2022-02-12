package com.example.mower_challenge.application.console.utils;

import com.example.mower_challenge.domain.Command;

import java.util.List;
import java.util.stream.Collectors;

public class CommandLineReader {

    private CommandLineReader(){

    }

    public static List<Command> getCommandList(String line){
        return line.chars()
                .mapToObj(c -> (char) c)
                .map(CommandLineReader::getComandFromString)
                .collect(Collectors.toList());
    }

    private static Command getComandFromString(Character command){
        switch (command){
            case 'M':
                return Command.FORWARD;
            case 'L':
                return Command.LEFT;
            case 'R':
                return Command.RIGHT;
            default:
                throw new RuntimeException("Invalid character");

        }
    }
}
