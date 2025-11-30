package br.vexy.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class HelpCommand implements Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Provides information about available commands.";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        String helpMessage = """
                Available Commands:
                /ping - Checks the bot's latency to Discord's gateway.
                /help - Provides information about available commands.""";
        event.reply(helpMessage).queue();
    }

}
