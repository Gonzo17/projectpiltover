package com.github.gonzo17.logic;


import com.github.gonzo17.db.MatchDbFacade;
import com.github.gonzo17.db.entities.MatchEntity;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.current_game.dto.CurrentGameInfo;
import net.rithms.riot.api.endpoints.current_game.dto.CurrentGameParticipant;
import net.rithms.riot.api.endpoints.game.dto.Game;
import net.rithms.riot.api.endpoints.game.dto.RecentGames;
import net.rithms.riot.constant.PlatformId;
import net.rithms.riot.constant.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProjectPiltoverLogic {

    private static final Logger log = LoggerFactory.getLogger(ProjectPiltoverLogic.class);

    @Autowired
    private MatchDbFacade dbFacade;

    @Value("${riot.api.key}")
    private String apiKey;

    private ApiConfig config;
    private RiotApi api;

    @PostConstruct
    public void init() {
        config = new ApiConfig().setKey(apiKey);
        api = new RiotApi(config);
    }

    public void updateMatchesForSummoner(List<Long> summoners) {
        for (Long summonerId : summoners) {
            try {
                RecentGames recentGames = api.getRecentGames(Region.EUW, summonerId);

                for (Game game : recentGames.getGames()) {
                    MatchEntity gameEntity =
                            MatchEntity.builder()
                                    .championId(game.getChampionId())
                                    .createDate(game.getCreateDate())
                                    .gameId(game.getGameId())
                                    .gameMode(game.getGameMode())
                                    .gameType(game.getGameType()).build();

                    dbFacade.saveMatch(gameEntity);
                }


            } catch (RiotApiException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO save summoners in db and get them from db here
    public List<Long> getSummonerIdsToUpdate() {
        return Stream.of("GonzoRocks").map(this::convertSummonerNameToId).collect(Collectors.toList());
    }


    // TODO can probably be removed if we save summoner information in our own db
    private long convertSummonerNameToId(String summoner) {
        try {
            return api.getSummonerByName(Region.EUW, summoner).getId();
        } catch (RiotApiException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String convertSummonerIdToName(Long summoner) {
        try {
            return api.getSummonerById(Region.EUW, summoner).getName();
        } catch (RiotApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String checkForCurrentGame(Long summonerId) {
        try {
            CurrentGameInfo currentGameInfo = api.getCurrentGameInfo(PlatformId.EUW, summonerId);
            String gameMode = currentGameInfo.getGameMode();
            CurrentGameParticipant summoner = currentGameInfo.getParticipants().stream().filter(p -> p.getSummonerId() == summonerId).findFirst().get();
            int championId = summoner.getChampionId();
            String summonerName = convertSummonerIdToName(summonerId);
            String championName = getChampionName(championId);
            return summonerName + " is playing " + championName + " in " + gameMode + ".";
        } catch (RiotApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getChampionName(int championId) {
        try {
            return api.getDataChampion(Region.EUW, championId).getName();
        } catch (RiotApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
