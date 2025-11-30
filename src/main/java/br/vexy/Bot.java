package br.vexy;

import br.vexy.commands.HelpCommand;
import br.vexy.commands.InfoCommand;
import br.vexy.commands.PingCommand;
import br.vexy.config.BotConfig;
import br.vexy.listeners.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Bot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    private static JDA jda;

    public static void main(String[] args) {
        final String botToken = BotConfig.getBotToken();

        try {
            // Build JDA instance
            jda = JDABuilder.createDefault(botToken)
                    .enableIntents(
                            GatewayIntent.MESSAGE_CONTENT,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_MEMBERS
                    )
                    .addEventListeners(new CommandListener())
                    .build();

            jda.awaitReady();
            logger.info("Bot is online and ready!");

            registerSlashCommands();
        } catch (InterruptedException error) {
            logger.error("Error starting the bot: ", error);
        }
    }

    private static void registerSlashCommands() {
        if (jda == null) {
            logger.error("JDA instance is not initialized. Cannot register slash commands.");
            return;
        }

        logger.info("Registering Slash Commands...");

        PingCommand pingCmd = new PingCommand();
        HelpCommand helpCmd = new HelpCommand();
        InfoCommand infoCmd = new InfoCommand();

        jda.updateCommands()
                .addCommands(
                        Commands.slash(pingCmd.getName(), pingCmd.getDescription()),
                        Commands.slash(helpCmd.getName(), helpCmd.getDescription()),
                        Commands.slash(infoCmd.getName(), infoCmd.getDescription())
                )
                .queue(success -> logger.info("Slash commands registered successfully!"),
                        failure -> logger.error("Failed to register slash commands: ", failure));
    }
}
