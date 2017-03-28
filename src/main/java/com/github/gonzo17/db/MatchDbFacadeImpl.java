package com.github.gonzo17.db;

import com.github.gonzo17.db.entities.MatchEntity;
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
    public void saveMatch(MatchEntity gameEntity) {
        repository.save(gameEntity);
        log.info("Match with id " + gameEntity.getGameId() + " saved.");

        // show all matches for debug purposes
        log.info("All matches saved:");
        log.info("-------------------------------");
        for (MatchEntity match : repository.findAll()) {
            log.info(match.toString());
        }
        log.info("");
    }

}
