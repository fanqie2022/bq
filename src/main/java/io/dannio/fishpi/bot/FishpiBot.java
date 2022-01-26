package io.dannio.fishpi.bot;

import io.dannio.fishpi.service.FishpiService;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.util.List;

import static io.dannio.fishpi.util.JsonUtils.toJson;

@Slf4j
@Getter
@Component
public class FishpiBot extends SpringWebhookBot {

    private final SetWebhook setWebhook;

    private final FishpiService service;

    private final String botUsername;

    private final String botToken;

    private final String botPath;


    public FishpiBot(SetWebhook setWebhook,
                     FishpiService service,
                     @Value("${bot.username}") String botUsername,
                     @Value("${bot.token}")    String botToken,
                     @Value("${bot.path}")     String botPath) {
        super(setWebhook);
        this.service = service;
        this.setWebhook = setWebhook;
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
    }


    @Override
    @SneakyThrows
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        log.info("Webhook received update[{}]", toJson(update));

        service.receive(update);

        List<PartialBotApiMethod<Message>> messages =  service.receive(update);
        messages.forEach(this::executeMessage);
        log.info("Webhook answer update[id:{}] messages size[{}] ", update.getUpdateId(), messages.size());

        return null;
    }


    @SneakyThrows
    private void executeMessage(PartialBotApiMethod<Message> message) {
        log.info("Webhook execute message[{}]", toJson(message));
        if (message instanceof SendMessage) {
            execute((SendMessage) message);
        }
        if (message instanceof SendPhoto) {
            execute((SendPhoto) message);
        }
    }


}
