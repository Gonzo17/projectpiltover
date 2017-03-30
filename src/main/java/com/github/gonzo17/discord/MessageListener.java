package com.github.gonzo17.discord;

import com.github.gonzo17.discord.MessageCommands.MessageCommand;
import com.github.gonzo17.discord.MessageCommands.SimpleTextCommand;
import com.github.gonzo17.discord.MessageCommands.SummonerCommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.rithms.riot.api.RiotApi;

import java.util.ArrayList;
import java.util.List;

public class MessageListener extends ListenerAdapter {

    private RiotApi api;
    private List<MessageCommand> messageCommands;

    public MessageListener(RiotApi api) {
        this.api = api;
        initMessageCommands();
    }

    private void initMessageCommands() {
        messageCommands = new ArrayList<>();

        messageCommands.add(new SimpleTextCommand("!ping", "!pong"));
        messageCommands.add(new SimpleTextCommand("!goblin", "Manuel, member of g3ck0 ..."));
        messageCommands.add(new SummonerCommand("!rank",
                "$summonerName is currently ranked in $summonerLeague $summonerDivision with $summonerLeaguePoints LP and a Win/Loss-Ratio of $summonerWinLossRatio", api));
        //TODO Somehow there are no pentakills for any player, which is in fact not true regarding one of my last games:
        //TODO http://matchhistory.euw.leagueoflegends.com/en/#match-details/EUW1/3121355746/27350694?tab=stats
//        messageCommands.add(new SummonerCommand("!pentas",
//                "$summonerName already has $amountPenta pentakills !!!", api));
        messageCommands.add(new SummonerCommand("!id",
                "Id of summoner $summonerName is $id", api));
        // TODO The interface db-objects need some logic to handle specific calls like "bestChmapion"
        //        messageCommands.add(new SummonerCommand("!bestChampion",
//                "The best choice for $summonerName is $bestChampion", api));
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        for (MessageCommand cmd : messageCommands) {
            cmd.setMessage(message.getContent());
            if (cmd.checkKeyPhraseTriggered()) {
                cmd.execute(channel);
            }
        }

        System.out.printf("(%s)[%s]<%s>: %s\n", event.getGuild().getName(), event.getChannel().getName(), event.getMember().getEffectiveName(), message.getContent());
    }
}