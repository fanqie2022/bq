package io.dannio.fishpi.bot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

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

    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(update.getMessage().getChatId()));
            message.setText("回复的内容");
            execute(message);
        }

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
        return "";
    }
}
