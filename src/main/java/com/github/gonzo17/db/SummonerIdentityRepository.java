package com.github.gonzo17.db;

import com.github.gonzo17.db.entities.match.summoner.SummonerIdentityEntity;
import org.springframework.data.repository.CrudRepository;

public interface SummonerIdentityRepository extends CrudRepository<SummonerIdentityEntity, Long> {

    Iterable<SummonerIdentityEntity> findByWatchTrue();

    boolean existsBySummonerName(String summonerName);
}
