package com.example.mower_challenge.domain.service;

import com.example.mower_challenge.domain.Garden;
import lombok.AllArgsConstructor;
import com.example.mower_challenge.domain.Command;
import com.example.mower_challenge.domain.Mower;
import com.example.mower_challenge.domain.repository.MowerRepository;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class MowerServiceSequential implements MowerService{
    private Garden garden;
    private MowerRepository mowerRepository;

    @Override
    public void executePlan(Map<Mower, List<Command>> executionPlan){
        executionPlan.keySet().forEach(m -> {
            executionPlan.get(m).stream().forEach(c -> {
                m.executeCommand(c);
                garden.verifyCoordinates(m.getCoordinate());
            });
            garden.useCoordinate(m.getCoordinate());
            mowerRepository.save(m);
        });

    }
}
