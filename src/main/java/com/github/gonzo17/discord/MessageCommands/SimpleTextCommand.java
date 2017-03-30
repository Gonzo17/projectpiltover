package com.github.gonzo17.discord.MessageCommands;


import net.dv8tion.jda.core.entities.MessageChannel;

public class SimpleTextCommand implements MessageCommand {

    private String keyPhrase = "";
    private String answer = "";
    private String message = "";

    public SimpleTextCommand(String keyPhrase, String answer) {
        this.keyPhrase = keyPhrase;
        this.answer = answer;
    }

    @Override
    public boolean checkKeyPhraseTriggered() {
        return message.startsWith(keyPhrase);
    }

    @Override
    public void execute(MessageChannel channel) {
        channel.sendMessage(answer).queue();
    }

    @Override
    public void setMessage(String content) {
        this.message = content;
    }

}
