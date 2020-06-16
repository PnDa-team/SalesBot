package com.panda.handler;

import com.panda.Bot;
import com.panda.command.Command;
import com.panda.command.ParsedCommand;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author carbon
 * class created 16.06.2020
 *
 * Займется обработкой базовых команд, таких как start, help и id
 */
public class SystemHandler extends AbstractHandler {

    private static final Logger log = Logger.getLogger(SystemHandler.class);
    private final String END_LINE = "\n";

    public SystemHandler(Bot bot) {
        super(bot);
    }

    /**
     * Формируем текстовые сообщения и складываем их в очередь на отправку. На этом работа хендлера прекращается.
     * Кто и как отправит эти сообщения — его совершенно не волнует.
     *
     * @param chatId
     * @param parsedCommand
     * @param update
     * @return
     */
    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();

        switch (command) {
            case START:
                bot.sendQueue.add(getMessageStart(chatId));
                break;
            case HELP:
                bot.sendQueue.add(getMessageHelp(chatId));
                break;
            case STICKER:
                return "StickerID: " + parsedCommand.getText();
            case ID:
                return "Your telegramID: " + update.getMessage().getFrom().getId();
        }
        return "";
    }

    private SendMessage getMessageHelp(String chatID) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);

        StringBuilder text = new StringBuilder();
        text.append("*This is help message*").append(END_LINE).append(END_LINE);
        text.append("[/start](/start) - show start message").append(END_LINE);
        text.append("[/help](/help) - show help message").append(END_LINE);
        text.append("[/id](/id) - know your ID in telegram ").append(END_LINE);
        text.append("/*notify* _time-in-sec_  - receive notification from me after the specified time").append(END_LINE);

        sendMessage.setText(text.toString());
        return sendMessage;
    }

    private SendMessage getMessageStart(String chatID) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);
        StringBuilder text = new StringBuilder();
        text.append("Hello. I'm  *").append(bot.getName()).append("*").append(END_LINE);
        text.append("All that I can do - you can see calling the command [/help](/help)");
        sendMessage.setText(text.toString());
        return sendMessage;
    }
}
