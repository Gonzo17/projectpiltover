package com.github.gonzo17;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.game.dto.Game;
import net.rithms.riot.api.endpoints.game.dto.RecentGames;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GameUpdateTask {

    private static final Logger log = LoggerFactory.getLogger(GameUpdateTask.class);

    private ApiConfig config = new ApiConfig().setKey("RGAPI-2A9F2703-382F-4B71-9FCE-28C22B2B354E");
    private RiotApi api = new RiotApi(config);

    @Autowired
    private GameRepository repository;

    @Scheduled(fixedDelay = 10000)
    public void updateRecentMatches() throws RiotApiException {
        Summoner summoner = api.getSummonerByName(Region.EUW, "GonzoRocks");
        long summonerId = summoner.getId();

        RecentGames recentGames = api.getRecentGames(Region.EUW, summonerId);

        for (Game game : recentGames.getGames()) {
            GameEntity gameEntity =
                    GameEntity.builder()
                            .championId(game.getChampionId())
                            .createDate(game.getCreateDate())
                            .gameId(game.getGameId())
                            .gameMode(game.getGameMode())
                            .gameType(game.getGameType()).build();

            repository.save(gameEntity);
        }

        // fetch all customers
        log.info("Games found with findAll():");
        log.info("-------------------------------");
        for (GameEntity gameEntity : repository.findAll()) {
            log.info(gameEntity.toString());
        }
        log.info("");
    }
}
