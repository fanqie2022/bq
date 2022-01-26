package io.dannio.fishpi.bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Slf4j
@Getter
public class FishpiBot extends SpringWebhookBot {

    private final String botUsername;

    private final String botToken;

    private final String botPath;


    public FishpiBot(SetWebhook setWebhook, String botUsername, String botToken, String botPath) {
        super(setWebhook);
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
    }


    public FishpiBot(DefaultBotOptions options, SetWebhook setWebhook, String botUsername, String botToken, String botPath) {
        super(options, setWebhook);
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        log.info("webhook received [{}]", update);
        final SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText("copied");
        return message;
    }


}
