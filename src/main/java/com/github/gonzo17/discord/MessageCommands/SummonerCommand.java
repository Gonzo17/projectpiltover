package com.github.gonzo17.discord.MessageCommands;

import net.dv8tion.jda.core.entities.Message;
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
public class SummonerCommand implements MessageCommand {

    private String keyPhrase = "";
    private KeyPhraseCheck keyPhraseCheck;
    private Message message;
    private RiotApi api;
    private String outputFormat;

    /**
     * Constructor for a SummonerCommand, specialized on commands regarding summoner information.
     *
     * @param keyPhrase      triggering word or sentence (highly recommended to start with a !-sign
     * @param keyPhraseCheck Decides how to the input should be compared to the keyphrase
     * @param outputFormat   Contains a string with placeholders, that will be replaced by actual values
     * @param api            RiotApi is necessary to retrieve the summoner information
     */
    public SummonerCommand(String keyPhrase, KeyPhraseCheck keyPhraseCheck, String outputFormat, RiotApi api) {
        this.keyPhrase = keyPhrase;
        this.keyPhraseCheck = keyPhraseCheck;
        this.outputFormat = outputFormat;
        this.api = api;
    }

    @Override
    public boolean checkKeyPhraseTriggered(Message input) {
        this.message = input;

        switch (keyPhraseCheck) {
            case STARTS_WITH:
                return input.getContent().startsWith(keyPhrase);
            case EQUALS:
                return keyPhrase.equals(input.getContent());
        }

        return false;
    }

    /**
     * @param channel The channel the message came from and where the response will be sent to
     */
    // TODO If possible, get these summoner information from DB, to lower load on the API limits (timestamping entries, add db entry if user is new)
    @Override
    public void execute(MessageChannel channel) {
        String summonerName = message.getContent().substring("!rank".length()).trim();
        String summonerLeague = "";
        String summonerDivision = "";
        String summonerLeaguePoints = "";
        String summonerWinLossRatio = "";

        try {
            Summoner summoner = api.getSummonerByName(Region.EUW, summonerName);
            League league = api.getLeagueEntryBySummoner(Region.EUW, summoner.getId()).get(0);

            summonerLeague = league.getTier();
            LeagueEntry entry = league.getEntries().get(0);
            summonerDivision = entry.getDivision();
            summonerLeaguePoints = String.valueOf(entry.getLeaguePoints());

            double amountOfGames = entry.getLosses() + entry.getWins();
            double percentage = Math.round(entry.getWins() * 100 / amountOfGames);
            summonerWinLossRatio = String.valueOf(entry.getWins()) + "/" + String.valueOf(entry.getLosses()) + " (" + percentage + "%)";

        } catch (RiotApiException e) {
            e.printStackTrace();
        }

        outputFormat = outputFormat.replaceAll("\\$summonerName", summonerName);
        outputFormat = outputFormat.replaceAll("\\$summonerLeague", summonerLeague);
        outputFormat = outputFormat.replaceAll("\\$summonerDivision", summonerDivision);
        outputFormat = outputFormat.replaceAll("\\$summonerLeaguePoints", summonerLeaguePoints);
        outputFormat = outputFormat.replaceAll("\\$summonerWinLossRatio", summonerWinLossRatio);

        channel.sendMessage(outputFormat).queue();
    }
}
