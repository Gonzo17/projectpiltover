package com.github.gonzo17.discord;

import com.github.gonzo17.discord.MessageCommands.KeyPhraseCheck;
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

        messageCommands.add(new SimpleTextCommand("!ping", KeyPhraseCheck.EQUALS, "!pong"));
        messageCommands.add(new SimpleTextCommand("!goblin", KeyPhraseCheck.EQUALS, "Manuel, member of g3ck0 ..."));
        messageCommands.add(new SummonerCommand("!rank",
                KeyPhraseCheck.STARTS_WITH,
                "$summonerName is currently ranked in $summonerLeague $summonerDivision with $summonerLeaguePoints LP and a Win/Loss-Ratio of $summonerWinLossRatio",
                api));
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        for (MessageCommand cmd : messageCommands) {
            if (cmd.checkKeyPhraseTriggered(message)) {
                cmd.execute(channel);
            }
        }

        System.out.printf("(%s)[%s]<%s>: %s\n", event.getGuild().getName(), event.getChannel().getName(), event.getMember().getEffectiveName(), message.getContent());
    }
}