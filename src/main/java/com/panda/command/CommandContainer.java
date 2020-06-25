package com.panda.command;

import com.panda.Bot;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static String chatId;
    private static ParsedCommand parsedCommand;
    private static Update update;
    private static Bot bot;

    public CommandContainer(String chatId, ParsedCommand parsedCommand, Update update, Bot bot) {
        this.chatId = chatId;
        this.parsedCommand = parsedCommand;
        this.update = update;
        this.bot = bot;

    }

    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<Command, SendMessage> commands = new TreeMap<>();

    static {
        commands.put(Command.START, new StartCommand(bot).operate(chatId, parsedCommand, update));
        commands.put(Command.NONE, new NoneCommand(bot).operate(chatId, parsedCommand, update));
    }

    public static SendMessage get(Command commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            log.trace("Command not found, name --> " + commandName);
            return commands.get(Command.NONE);
        }
        return commands.get(commandName);
    }

}
