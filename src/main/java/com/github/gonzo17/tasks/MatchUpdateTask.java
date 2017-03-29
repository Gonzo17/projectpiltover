package com.github.gonzo17.tasks;

import com.github.gonzo17.logic.ProjectPiltoverLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchUpdateTask {

    private static final Logger log = LoggerFactory.getLogger(MatchUpdateTask.class);

    @Autowired
    private ProjectPiltoverLogic logic;

    @Scheduled(fixedDelay = 100000, initialDelay = 60000)
    public void updateRecentMatches() {
        List<Long> summoners = logic.getSummonerIdsToUpdate();
        for (Long summonerId : summoners) {
            logic.updateMatchesForSummoner(summonerId);
        }
    }
}
