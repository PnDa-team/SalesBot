package com.panda.service;

import com.panda.Bot;
import com.panda.Parser;
import com.panda.command.Command;
import com.panda.command.CommandContainer;
import com.panda.command.ParsedCommand;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author carbon
 * class created 16.06.2020
 * <p>
 * обработчик полученных сообщений
 */
public class MessageReciever implements Runnable {

    private static final Logger log = Logger.getLogger(MessageReciever.class);
    private final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;
    private Bot bot;
    private Parser parser;

    public MessageReciever(Bot bot) {
        this.bot = bot;
        parser = new Parser(bot.getName());
    }

    /**
     * в бесконечном цикле будет брать принятые сообщения, обрабатывать их
     * и складывать ему же в очередь для отправки результаты своей работы
     */
    @Override
    public void run() {
        log.trace("[STARTED] MsgReciever.  Bot class: " + bot);
        while (true) {
            for (Object object = bot.receiveQueue.poll(); object != null; object = bot.receiveQueue.poll()) {
                log.debug("New object for analyze in queue " + object.toString());
                analyze(object); //Если что-то есть — запускаем анализатор
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            }
        }
    }

    /**
     * Анализатор проверяет тип объекта. Если он умеет с ним работать — запускает следующий анализатор.
     * Если не умеет — ругается :)
     *
     * @param object
     */
    private void analyze(Object object) {
        if (object instanceof Update) {
            Update update = (Update) object;
            log.debug("Update recieved: " + update.toString());
            analyzeForUpdateType(update);
        } else {
            log.warn("Cant operate type of object: " + object.toString());
        }
    }

    /**
     * Определяет ID чата. Получает текст сообщения. С помощью парсера определяет,
     * является ли сообщение командой и определяет каким хендлером данную команду нужно обрабатывать.
     * Запускает обработку команды и если обработка команды вернула какой-то непустой текст
     * — формирует сообщение для отправки пользователю и складывает его в очередь.
     *
     * @param update
     */
    private void analyzeForUpdateType(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        ParsedCommand parsedCommand = new ParsedCommand(Command.NONE, "");

        if (message.hasText()||update.hasCallbackQuery()) {
            parsedCommand = parser.getParsedCommand(message.getText());
        }
        SendMessage result = CommandContainer.get(parsedCommand.getCommand());
//        if (!"".equals(result)) {
//            SendMessage messageOut = new SendMessage();
//            messageOut.setChatId(chatId);
//            messageOut.setText(result);
//
//            bot.sendQueue.add(messageOut);
//        }
        result.setChatId(chatId);
        bot.sendQueue.add(result);
    }

}
