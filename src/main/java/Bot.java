import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author carbon
 * class created 13.06.2020
 */
public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            /*SendMessage message = new SendMessage()
                    .setChatId(chat_id)
                    .setText(message_text);
            try {
                execute(message); // Sending message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }*/


            //TODO определить вид команды которая пришла, вызвать соответствующий класс и вызвать в нем execute
        }
    }

    public String getBotUsername() {
        return "PandaBot";
    }

    public String getBotToken() {
        String token = System.getProperty("token");
        return token;
    }
}
