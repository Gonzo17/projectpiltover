package com.github.gonzo17.mapper;

import com.github.gonzo17.db.entities.match.summoner.SummonerIdentityEntity;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import org.springframework.stereotype.Component;

@Component
public class SummonerMapper {

    public SummonerIdentityEntity mapSummonerToEntity(Summoner summoner) {
        return SummonerIdentityEntity.builder()
                .summonerId(summoner.getId())
                .summonerName(summoner.getName())
                .profileIcon(summoner.getProfileIconId())
                .revisionDate(summoner.getRevisionDate())
                .summonerLevel(summoner.getSummonerLevel())
                .build();
    }
}
