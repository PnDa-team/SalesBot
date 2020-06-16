package com.panda;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author carbon
 * class created 13.06.2020
 */
public class Bot extends TelegramLongPollingBot {

    private static final Logger log = Logger.getLogger(Bot.class);
    final int RECONNECT_PAUSE =10000;
    private String name;
    private String token;
    public final Queue<Object> sendQueue = new ConcurrentLinkedQueue<>(); //мы не привязываемся к типам полученных сообщений
    public final Queue<Object> receiveQueue = new ConcurrentLinkedQueue<>();

    public Bot() {
    }

    public Bot(String name, String token) {
        this.name = name;
        this.token = token;
    }

    public Bot(DefaultBotOptions options, String name, String token) {
        super(options);
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Receive new Update. updateID: " + update.getUpdateId());
        receiveQueue.add(update);
    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
            log.info("[STARTED] TelegramAPI. Bot Connected. Bot class: " + this);
        } catch (TelegramApiRequestException e) {
            log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }

    public String getBotUsername() {
//        log.debug("Bot name: " + name);
        return name;
    }

    public String getBotToken() {
//        log.debug("Bot token: " + token);
        return token;
    }
}
