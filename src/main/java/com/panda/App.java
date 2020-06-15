package com.panda;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * @author carbon
 * class created 13.06.2020
 */
public class App {
    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {

        ApiContextInitializer.init();
        Bot bot = new Bot("PandaBot", System.getProperty("token"));
        bot.botConnect();
    }
}
