package com.example.mower_challenge.application.console;

import com.example.mower_challenge.application.console.utils.CommandLineReader;
import com.example.mower_challenge.application.console.utils.GardenLineReader;
import com.example.mower_challenge.application.console.utils.MowerLineReader;
import com.example.mower_challenge.domain.Command;
import com.example.mower_challenge.domain.Garden;
import com.example.mower_challenge.domain.Mower;
import com.example.mower_challenge.domain.service.MowerService;
import com.example.mower_challenge.domain.service.MowerServiceSequential;
import com.example.mower_challenge.infrastructure.repository.MowerOutputRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class MowerFileProcessor {
    private Map<Mower, List<Command>> executionPlan;
    private Garden garden;
    private MowerService mowerService;

    public MowerFileProcessor(String fileName) throws FileNotFoundException {

        List<String> fileLine = readFile(fileName);
        garden = GardenLineReader.getGarden(fileLine.get(0));

        fileLine.remove(0);
        executionPlan = new LinkedHashMap<>();

        for(int i = 0; i < fileLine.size(); i += 2){
            Mower mower = MowerLineReader.getMower(fileLine.get(i));
            List<Command> commands = CommandLineReader.getCommandList(fileLine.get(i + 1));
            executionPlan.put(mower, commands);
        }

        mowerService = new MowerServiceSequential(garden, new MowerOutputRepository());
    }

    public void runMower(){
        mowerService.executePlan(executionPlan);
    }


    private List<String> readFile(final String fileName) throws FileNotFoundException {
        List<String> fileLine = new ArrayList<String>();

        File file = new File(fileName);

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileLine.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw e;
        }
        return fileLine;
    }





}
