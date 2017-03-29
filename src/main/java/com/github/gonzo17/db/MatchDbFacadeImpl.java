package com.github.gonzo17.db;

import com.github.gonzo17.db.entities.match.MatchEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatchDbFacadeImpl implements MatchDbFacade {

    private static final Logger log = LoggerFactory.getLogger(MatchDbFacade.class);

    @Autowired
    private MatchRepository repository;

    @Override
    public void saveMatch(MatchEntity matchEntity) {
        repository.save(matchEntity);
        log.info("Match with id " + matchEntity.getMatchId() + " saved.");
    }

    @Override
    public boolean hasMatchWithId(long gameId) {
        return repository.exists(gameId);
    }

}
