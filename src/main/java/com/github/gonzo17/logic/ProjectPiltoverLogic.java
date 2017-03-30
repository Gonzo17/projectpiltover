package com.github.gonzo17.logic;


import com.github.gonzo17.db.MatchDbFacade;
import com.github.gonzo17.db.SummonerDbFacade;
import com.github.gonzo17.db.SummonerDbImpl;
import com.github.gonzo17.db.entities.match.MatchEntity;
import com.github.gonzo17.db.entities.match.summoner.SummonerIdentityEntity;
import com.github.gonzo17.discord.MessageListener;
import com.github.gonzo17.mapper.MatchMapper;
import com.github.gonzo17.mapper.SummonerMapper;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.current_game.dto.CurrentGameInfo;
import net.rithms.riot.api.endpoints.current_game.dto.CurrentGameParticipant;
import net.rithms.riot.api.endpoints.game.dto.Game;
import net.rithms.riot.api.endpoints.game.dto.RecentGames;
import net.rithms.riot.api.endpoints.league.dto.League;
import net.rithms.riot.api.endpoints.league.dto.LeagueEntry;
import net.rithms.riot.api.endpoints.match.dto.MatchDetail;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.PlatformId;
import net.rithms.riot.constant.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.stream.Stream;

import static net.rithms.riot.api.RiotApiException.DATA_NOT_FOUND;

@Component
public class ProjectPiltoverLogic {

    private static final Logger log = LoggerFactory.getLogger(ProjectPiltoverLogic.class);

    @Autowired
    private MatchDbFacade matchDbFacade;
    @Autowired
    private SummonerDbFacade summonerDbFacade;
    @Autowired
    private SummonerDbImpl summonerDb;
    @Autowired
    private MatchMapper matchMapper;
    @Autowired
    private SummonerMapper summonerMapper;

    @Value("${riot.api.key}")
    private String apiKey;

    @Value("${discord.app.bot.token}")
    private String discordAppBotToken;

    private ApiConfig config;
    private RiotApi api;
    private JDA jda;

    @PostConstruct
    public void init() {
        config = new ApiConfig().setKey(apiKey);
        api = new RiotApi(config);
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(discordAppBotToken).addListener(new MessageListener(api)).buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
    }

    public void updateMatchesForSummoner(Long summonerId) {
        try {
            RecentGames recentGames = api.getRecentGames(Region.EUW, summonerId);
            for (Game game : recentGames.getGames()) {
                long matchId = game.getGameId();
                if (matchDbFacade.hasMatchWithId(matchId)) {
                    continue;
                }
                MatchDetail matchDetail = api.getMatch(Region.EUW, matchId);
                MatchEntity matchEntity = matchMapper.mapMatchDetailToMatchEntity(matchDetail);
                matchEntity.setSummonerId(summonerId);
                matchDbFacade.saveMatch(matchEntity);
            }
        } catch (RiotApiException e) {
            e.printStackTrace();
        }
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
            if (e.getErrorCode() != DATA_NOT_FOUND) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void initializeSummoners() {
        Stream.of("GonzoRocks", "TheRealGoblin").forEach(this::initializeSummoner);
    }

    public void initializeSummoner(String summonerName) {
        if (summonerDbFacade.exists(summonerName)) {
            return;
        }

        try {
            Summoner summoner = api.getSummonerByName(Region.EUW, summonerName);
            SummonerIdentityEntity summonerIdentityEntity = summonerMapper.mapSummonerToEntity(summoner);
            summonerIdentityEntity.setWatch(true);
            summonerDbFacade.save(summonerIdentityEntity);
        } catch (RiotApiException e) {
            e.printStackTrace();
        }
    }

    public void updateSummonerInformation() {
        // TODO check on revision date whether or not we should update
    }

    public List<Long> getSummonerIdsToUpdate() {
        return summonerDbFacade.getSummonersIdsToWatch();
    }

    public Summoner getSummonerByName(Region euw, String summonerName) {
        if (summonerDb.exists(summonerName)) {

        }

        return new Summoner();
    }

    public String getLeagueBySummonerId(long summonerId) {
        return new League().getTier();
    }

    public String getDivisionBySummonerId(long summonerId) {
        return new LeagueEntry().getDivision();
    }

    public String getLeaguePointsBySummonerId(long summonerId) {
        return String.valueOf(new LeagueEntry().getLeaguePoints());
    }

    public int getAmountTotalRankedGamesBySummonerId(long summonerId) {
        return (getAmountRankedGameLossesBySummonerId(summonerId) + getAmountRankedGameWinsBySummonerId(summonerId));
    }

    public int getAmountRankedGameWinsBySummonerId(long summonerId) {
        return new LeagueEntry().getWins();
    }

    public int getAmountRankedGameLossesBySummonerId(long summonerId) {
        return new LeagueEntry().getLosses();
    }

    private String convertSummonerIdToName(Long summoner) {
        return summonerDbFacade.getSummonerById(summoner).getSummonerName();
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
