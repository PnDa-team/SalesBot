package com.panda.handler;

import com.panda.Bot;
import com.panda.command.ParsedCommand;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author carbon
 * class created 16.06.2020
 *
 * хендлер, который не будет делать ничего и использовать мы его будем тогда,
 * когда не сможем понять что за тип команды нам передали и от бота никакой реакции не требуется.
 */
public class DefaultHandler extends AbstractHandler {

    private static final Logger log = Logger.getLogger(DefaultHandler.class);

    public DefaultHandler(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        return "";
    }
}
