package com.github.gonzo17.discord.MessageCommands;


import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class SimpleTextCommand implements MessageCommand {

    private String keyPhrase = "";
    private String answer = "";
    private KeyPhraseCheck keyPhraseCheck;

    public SimpleTextCommand(String keyPhrase, KeyPhraseCheck keyPhraseCheck, String answer) {
        this.keyPhrase = keyPhrase;
        this.answer = answer;
        this.keyPhraseCheck = keyPhraseCheck;
    }

    @Override
    public boolean checkKeyPhraseTriggered(Message input) {
        switch (keyPhraseCheck) {
            case STARTS_WITH:
                return input.getContent().startsWith(keyPhrase);
            case EQUALS:
                return keyPhrase.equals(input.getContent());
        }

        return false;
    }

    @Override
    public void execute(MessageChannel channel) {
        channel.sendMessage(answer).queue();
    }

}
