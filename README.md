
# Mower Kata Challenge

## Assumptions

- The coordinates (for both `Garden` and `Mower`) are always positive numbers.
- The mower executes the command, and then it will check if the `Coordinate` is valid and empty.
    + If there is any problem when executing the command routine, the mower will stop.
- The application will read the whole configuration and commands from a text file following the given description in the statement.
- An example input file is given in the following path `src/main/java/resources/input.txt`.

# HOW-TO

## Build the project

In the `rootDirectory` execute:

> mvn clean install

## Execute tests

In the `rootDirectory` execute:

> mvn clean test

## Execute the application

In the `rootDirectory/target` execute:

> java -jar mower_challenge-1.0.jar <input_file>

where `<input_file>` is the path to the input file (there is an example in the `resources` directory)