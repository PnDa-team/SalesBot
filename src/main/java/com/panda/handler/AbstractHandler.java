package com.panda.handler;

import com.panda.Bot;
import com.panda.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author carbon
 * class created 16.06.2020
 */
public abstract class AbstractHandler {
    Bot bot;

    public AbstractHandler(Bot bot) {
        this.bot = bot;
    }

    public abstract SendMessage operate(String chatId, ParsedCommand parsedCommand, Update update);
}
