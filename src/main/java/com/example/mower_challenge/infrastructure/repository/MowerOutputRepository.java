package com.example.mower_challenge.infrastructure.repository;

import com.example.mower_challenge.domain.Mower;
import com.example.mower_challenge.domain.repository.MowerRepository;

public class MowerOutputRepository implements MowerRepository {

    @Override
    public void save(Mower mower) {
        System.out.println(mower.getCoordinate().getX() + " " + mower.getCoordinate().getY() + " " + mower.getCardinalPoint().getCode());
    }
}
