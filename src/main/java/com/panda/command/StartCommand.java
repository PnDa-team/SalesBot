package com.panda.command;

import com.panda.Bot;
import com.panda.handler.AbstractHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class StartCommand extends AbstractHandler {

    StartCommand(Bot bot) {
        super(bot);
    }

    @Override
    public SendMessage operate(String chatId, ParsedCommand parsedCommand, Update update) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId);


        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//    TODO
//        settingKeyboard(replyKeyboardMarkup);
//        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
//        keyboard.clear();
//        KeyboardRow keyboardFirstRow = new KeyboardRow();
//        KeyboardRow keyboardSecondRow = new KeyboardRow();
//        KeyboardButton button = new KeyboardButton();
//        button.setText("sss").;
//        keyboardFirstRow.add();
//        keyboardSecondRow.add("Продавец");
//        keyboard.add(keyboardFirstRow);
//        keyboard.add(keyboardSecondRow);
//        replyKeyboardMarkup.setKeyboard(keyboard);

        return sendMessage.setChatId(chatId).setText("Хто ты?").setReplyMarkup(replyKeyboardMarkup);
//        return new SendMessage().setChatId(chatId).setText("Привет,я бооотттт").setReplyMarkup(replyKeyboardMarkup);
    }

    private void settingKeyboard(ReplyKeyboardMarkup replyKeyboardMarkup) {
        replyKeyboardMarkup.setSelective(true).setResizeKeyboard(true).setOneTimeKeyboard(false);
    }
}
