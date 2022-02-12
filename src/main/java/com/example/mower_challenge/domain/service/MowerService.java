package com.example.mower_challenge.domain.service;

import com.example.mower_challenge.domain.Command;
import com.example.mower_challenge.domain.Mower;

import java.util.List;
import java.util.Map;

public interface MowerService {

    void executePlan(Map<Mower, List<Command>> executionPlan);
}
