package com.panda;

import com.panda.service.MessageReciever;
import com.panda.service.MessageSender;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * @author carbon
 * class created 13.06.2020
 */
public class App {

    private static final Logger log = Logger.getLogger(App.class);
    private static final int PRIORITY_FOR_SENDER = 1;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static final String ADMIN = "419567767";
    private static final String ADMIN2 = "491023314";


    public static void main(String[] args) {

        ApiContextInitializer.init();
        Bot bot = new Bot("PandaBot", System.getProperty("token"));

        MessageReciever messageReciever = new MessageReciever(bot);
        MessageSender messageSender = new MessageSender(bot);

        bot.botConnect();

        Thread receiver = new Thread(messageReciever);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.setPriority(PRIORITY_FOR_RECEIVER);
        receiver.start();

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(PRIORITY_FOR_SENDER);
        sender.start();


//        sendStartReport(bot, ADMIN);
//        sendStartReport(bot, ADMIN2);

    }

    private static void sendStartReport(Bot bot, String adminID) {
        log.info("Message send to admin");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(adminID);
        sendMessage.setText("PandaBot zапустился");
        bot.sendQueue.add(sendMessage);
    }
}
