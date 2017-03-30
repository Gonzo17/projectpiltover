package com.github.gonzo17.discord.MessageCommands;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.league.dto.League;
import net.rithms.riot.api.endpoints.league.dto.LeagueEntry;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Region;

/**
 * @author manuel
 * @version 1.0
 * @apiNote Currently it is possible to choose from five different summoner details to build a response Name, League, Division, LeaguePoints and WinLossRatio.
 */
//@Component
public class SummonerCommand implements MessageCommand {

//    @Autowired
//    private ProjectPiltoverLogic piltoverLogic;

    private String keyPhrase = "";
    private String message;
    private String outputFormat;
    private RiotApi api;

    /**
     * Constructor for a SummonerCommand, specialized on commands regarding summoner information.
     *
     * @param keyPhrase    triggering word or sentence (highly recommended to start with a !-sign
     * @param outputFormat Contains a string with placeholders, that will be replaced by actual values
     */
    public SummonerCommand(String keyPhrase, String outputFormat, RiotApi riotApi) {
        this.keyPhrase = keyPhrase;
        this.outputFormat = outputFormat;
        this.api = riotApi;
    }

    @Override
    public boolean checkKeyPhraseTriggered() {
        return message.startsWith(keyPhrase);
    }

    /**
     * @param channel The channel the message came from and where the response will be sent to
     */
    // TODO There are some issues when trying to add @Component to this class, regarding string parameter in constructor
    @Override
    public void execute(MessageChannel channel) {
        String result = outputFormat;

        String summonerName = message.substring("!rank".length()).trim();
        String summonerLeague = "";
        String summonerDivision = "";
        String summonerLeaguePoints = "";
        String summonerWinLossRatio = "";

//        Summoner summoner = piltoverLogic.getSummonerByName(Region.EUW, summonerName);
//        summonerLeague = piltoverLogic.getLeagueBySummonerId (summoner.getId()).toString();
//        summonerDivision = piltoverLogic.getDivisionBySummonerId (summoner.getId()).toString();
//        summonerLeaguePoints = piltoverLogic.getLeaguePointsBySummonerId (summoner.getId()).toString();
//
//        double amountOfGames = piltoverLogic.getAmountTotalRankedGamesBySummonerId(summoner.getId());
//        double amountOfWins = piltoverLogic.getAmountRankedGameWinsBySummonerId(summoner.getId());
//        summonerWinLossRatio = String.valueOf(amountOfWins) + "/" + String.valueOf(piltoverLogic.getAmountRankedGameLossesBySummonerId(summoner.getId())) + " (" + percentage + "%)";

        try {
            Summoner summoner = api.getSummonerByName(Region.EUW, summonerName);
            League league = api.getLeagueEntryBySummoner(Region.EUW, summoner.getId()).get(0);

            summonerLeague = league.getTier();
            LeagueEntry entry = league.getEntries().get(0);
            summonerDivision = entry.getDivision();
            summonerLeaguePoints = String.valueOf(entry.getLeaguePoints());

            int amountOfWins = entry.getWins();
            int amountOfLosses = entry.getLosses();
            int amountOfGames = amountOfWins + amountOfLosses;
            double percentage = Math.round(amountOfWins * 100 / amountOfGames);
            summonerWinLossRatio = String.valueOf(amountOfWins) + "/" + String.valueOf(amountOfLosses) + " (" + percentage + "%)";

        } catch (RiotApiException e) {
            e.printStackTrace();
        }

        result = result.replaceAll("\\$summonerName\\b", summonerName);
        result = result.replaceAll("\\$summonerLeague\\b", summonerLeague);
        result = result.replaceAll("\\$summonerDivision\\b", summonerDivision);
        result = result.replaceAll("\\$summonerLeaguePoints\\b", summonerLeaguePoints);
        result = result.replaceAll("\\$summonerWinLossRatio\\b", summonerWinLossRatio);

        channel.sendMessage(result).queue();
    }

    @Override
    public void setMessage(String content) {
        this.message = content;
    }
}
