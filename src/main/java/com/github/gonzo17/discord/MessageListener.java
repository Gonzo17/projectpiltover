package com.github.gonzo17.discord;

import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.league.dto.League;
import net.rithms.riot.api.endpoints.league.dto.LeagueEntry;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Region;

public class MessageListener extends ListenerAdapter {

    private RiotApi api;

    public MessageListener(RiotApi api) {
        this.api = api;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        Guild guild = event.getGuild();                 //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
        TextChannel textChannel = event.getChannel();   //The TextChannel that this message was sent to.
        Member member = event.getMember();              //Member that sent the message. Contains Guild specific information about the User!
        String name = member.getEffectiveName();        //This will either use the Member's nickname if they have one, otherwise it will default to their username. (User#getName())

        System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, message.getContent());

        // If you did message.equals() it will fail because you would be comparing a Message to a String!
        if (message.getContent().equals("!ping")) {
            channel.sendMessage("pong!").queue();
        }

        if (message.getContent().startsWith("!rank")) {
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

            channel.sendMessage(summonerName + " is currently ranked in " + summonerLeague + " " + summonerDivision + " with " + summonerLeaguePoints + "LP and a Win/Loss-Ratio of " + summonerWinLossRatio).queue();
        }

    }
}
