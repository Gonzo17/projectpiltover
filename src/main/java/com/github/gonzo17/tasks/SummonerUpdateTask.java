package com.github.gonzo17.tasks;

import com.github.gonzo17.ProjectPiltover;
import com.github.gonzo17.logic.ProjectPiltoverLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SummonerUpdateTask {

    @Autowired
    public ProjectPiltoverLogic logic;

    @Scheduled(fixedRate = 100000)
    public void initializeSummoners(){
        logic.initializeSummoners();
    }

    @Scheduled(cron = "0 0 6 1/1 * ?")
    public void updateSummonerInformation(){
        logic.updateSummonerInformation();
    }
}
