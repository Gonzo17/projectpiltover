package com.github.gonzo17.discord.MessageCommands;


import net.dv8tion.jda.core.entities.MessageChannel;

public interface MessageCommand {

    // Returns true if this command has to be executed
    boolean checkKeyPhraseTriggered();

    // Command to execute in specified discord channel
    void execute(MessageChannel channel);

    // Set the content of the message
    void setMessage(String content);
}
