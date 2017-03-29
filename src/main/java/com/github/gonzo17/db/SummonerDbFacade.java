package com.github.gonzo17.db;

import com.github.gonzo17.db.entities.match.summoner.SummonerIdentityEntity;

import java.util.List;

public interface SummonerDbFacade {
    void save(SummonerIdentityEntity summonerIdentityEntity);

    List<Long> getSummonersIdsToWatch();

    boolean exists(long summonerId);

    boolean exists(String summonerName);

    SummonerIdentityEntity getSummonerById(long summonerId);
}
