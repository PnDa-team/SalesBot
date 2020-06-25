package com.panda.command;

import com.panda.Bot;
import com.panda.handler.AbstractHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NoneCommand extends AbstractHandler {


    NoneCommand(Bot bot) {
        super(bot);
    }

    @Override
    public SendMessage operate(String chatId, ParsedCommand parsedCommand, Update update) {

        return new SendMessage().setChatId(chatId).setText("Нет такой команды");
    }
}
