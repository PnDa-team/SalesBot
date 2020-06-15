package command;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author carbon
 * class created 15.06.2020
 */
public abstract class AnonymizerCommand extends BotCommand {

    final Logger log = LogManager.getLogger(AnonymizerCommand.class);

    AnonymizerCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    public void execute(AbsSender sender, SendMessage message, User user) {
        try {
            sender.execute(message);
            log.info("success "+ user.getId()+ " "+ message);
        } catch (TelegramApiException e) {
            log.error("Error: ", e);
        }

    }

}
