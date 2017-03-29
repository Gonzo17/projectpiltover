package com.github.gonzo17.db;

import com.github.gonzo17.db.entities.match.summoner.SummonerIdentityEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class SummonerDbImpl implements SummonerDbFacade {

    private static final Logger log = LoggerFactory.getLogger(SummonerDbImpl.class);

    @Autowired
    private SummonerIdentityRepository summonerIdentityRepository;

    @Override
    public void save(SummonerIdentityEntity summonerIdentityEntity) {
        summonerIdentityRepository.save(summonerIdentityEntity);
        log.info("Summoner " + summonerIdentityEntity.getSummonerName() + " with id " + summonerIdentityEntity.getSummonerId() + " saved.");
    }

    @Override
    public List<Long> getSummonersIdsToWatch() {
        Iterable<SummonerIdentityEntity> summonerIdentityEntities = summonerIdentityRepository.findByWatchTrue();
        List<SummonerIdentityEntity> summonerList = new ArrayList<>();
        summonerIdentityEntities.forEach(summonerList::add);
        return summonerList.stream()
                .map(SummonerIdentityEntity::getSummonerId)
                .collect(toList());
    }

    @Override
    public boolean exists(long summonerId) {
        return summonerIdentityRepository.exists(summonerId);
    }

    @Override
    public boolean exists(String summonerName) {
        return summonerIdentityRepository.existsBySummonerName(summonerName);
    }

    @Override
    public SummonerIdentityEntity getSummonerById(long summonerId) {
        return summonerIdentityRepository.findOne(summonerId);
    }
}
