package io.dannio.fishpi.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Slf4j
public class FishpiBot extends SpringWebhookBot {


    public FishpiBot(SetWebhook setWebhook) {
        super(setWebhook);
    }

    public FishpiBot(DefaultBotOptions options, SetWebhook setWebhook) {
        super(options, setWebhook);
    }

    @Override
    public String getBotUsername() {
        return "fish_pi_bot";
    }

    @Override
    public String getBotToken() {
        return "5211063652:AAEhRVrSBXyaLnXG0BAEIbdnQW02mYNaeKA";
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        log.info("webhook received [{}]", update);
        final SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChat().getUserName());
        message.setText("copied");
        return message;
    }


    @Override
    public String getBotPath() {
        return "https://fishpi-bot.herokuapp.com/";
    }

}
