package io.dannio.fishpi.bot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Slf4j
public class FishpiBot extends TelegramWebhookBot {
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
