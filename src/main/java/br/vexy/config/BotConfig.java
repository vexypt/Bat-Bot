package br.vexy.config;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BotConfig {

    private static final Logger logger = LoggerFactory.getLogger(BotConfig.class);

    private static final String TOKEN_ENV_KEY = "BOT_TOKEN";

    private static final String botToken;

    // Prevents instantiation of this class.
    private BotConfig() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        String loadedToken = null;
        Dotenv dotenv = null;

        try {
            // 1. Attempt to load the .env file from the project root.
            dotenv = Dotenv.load();
            logger.info("Configuration: .env file successfully loaded.");

            // 2. Try to get the token from the loaded .env file.
            loadedToken = dotenv.get(TOKEN_ENV_KEY);
            if (loadedToken != null) {
                logger.info("Configuration: Token found in the .env file.");
            }
        } catch (DotenvException e) {
            // This occurs if the .env file is not found.
            logger.warn("Configuration: .env file not found or could not be parsed. Falling back to system environment variables.");
        }

        // 3. Final Validation: Assign or throw a fatal error.
        if (loadedToken == null || loadedToken.trim().isEmpty()) {
            logger.error("FATAL CONFIGURATION ERROR: The mandatory key '{}' was not defined in the .env file or system environment.", TOKEN_ENV_KEY);
            throw new RuntimeException("Missing mandatory configuration: Bot token not found for key '" + TOKEN_ENV_KEY + "'");
        } else {
            botToken = loadedToken;
            logger.info("Configuration: Bot token successfully finalized.");
        }
    }

    /**
     * Retrieves the Discord bot authorization token.
     *
     * @return The immutable Discord bot token string.
     */
    public static String getBotToken() {
        return botToken;
    }
}