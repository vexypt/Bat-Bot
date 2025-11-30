package br.vexy.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class InfoCommand implements Command {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Displays information about the bot.";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Bot Information");
        embedBuilder.setDescription("This is a simple Discord bot created using Java and JDA.");
        embedBuilder.setColor(new Color(148, 0, 211));
        embedBuilder.addField("Author", "Vexy", false);
        embedBuilder.addField("Language", "Java", true);
        embedBuilder.addField("Library", "JDA (Java Discord API)", true);
        embedBuilder.setFooter("Created with ❤️!");

        MessageEmbed embed = embedBuilder.build();
        event.replyEmbeds(embed).queue();
    }
}