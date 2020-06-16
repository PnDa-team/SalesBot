package com.panda.service;

import com.panda.Bot;
import com.panda.Parser;
import com.panda.command.Command;
import com.panda.command.ParsedCommand;
import com.panda.handler.AbstractHandler;
import com.panda.handler.DefaultHandler;
import com.panda.handler.SystemHandler;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;

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
        log.info("[STARTED] MsgReciever.  Bot class: " + bot);
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

        if (message.hasText()) {
            parsedCommand = parser.getParsedCommand(message.getText());
        } else {
            Sticker sticker = message.getSticker();
            if (sticker != null) {
                parsedCommand = new ParsedCommand(Command.STICKER, sticker.getFileId());
            }
        }

        AbstractHandler handlerForCommand = getHandlerForCommand(parsedCommand.getCommand());
        String operationResult = handlerForCommand.operate(chatId.toString(), parsedCommand, update);

        if (!"".equals(operationResult)) {
            SendMessage messageOut = new SendMessage();
            messageOut.setChatId(chatId);
            messageOut.setText(operationResult);
            bot.sendQueue.add(messageOut);
        }
    }



    /*TODO Теперь, если вы захотите добавить еще какие-то команды, нужно будет сделать следующее:
   В классе Command добавить синтаксис команды.
   getHandlerForCommand указать, кто будет ответственен за обработку этой команды.
   И собственно написать этот хендлер.

   либо создать мапу <команда-обработчик> и дальше распеределять (наподобие как в финальном)
    */
    private AbstractHandler getHandlerForCommand(Command command) {
        if (command == null) {
            log.warn("Null command accepted. This is not good scenario.");
            return new DefaultHandler(bot);
        }
        switch (command) {
            case START:
            case HELP:
            case ID:
                SystemHandler systemHandler = new SystemHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + systemHandler);
                return systemHandler;
            /*case NOTIFY:
                NotifyHandler notifyHandler = new NotifyHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + notifyHandler);
                return notifyHandler;*/
            case STICKER:
                SystemHandler s = new SystemHandler(bot);
                log.info("Handler for command[" + command.toString() + "] is: " + s);
                return s;
            default:
                log.warn("Handler for command[" + command.toString() + "] not Set. Return DefaultHandler");
                return new DefaultHandler(bot);
        }
    }
}
