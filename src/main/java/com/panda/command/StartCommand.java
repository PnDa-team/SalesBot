package com.panda.command;

import com.panda.Bot;
import com.panda.handler.AbstractHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand extends AbstractHandler {

    StartCommand(Bot bot) {
        super(bot);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        return "START COMMAND";
    }
}
