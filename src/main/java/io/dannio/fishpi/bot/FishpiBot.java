package io.dannio.fishpi.bot;

import io.dannio.fishpi.properties.BotProperty;
import io.dannio.fishpi.service.ChatroomService;
import io.dannio.fishpi.service.FishpiService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import javax.annotation.PostConstruct;
import java.util.List;

import static io.dannio.fishpi.util.JsonUtils.toJson;

@Slf4j
@Component
public class FishpiBot extends SpringWebhookBot {

    private final FishpiService service;

    private final ChatroomService chatroomService;

    private final BotProperty property;


    public FishpiBot(SetWebhook setWebhook, FishpiService service, ChatroomService chatroomService, BotProperty property) {
        super(setWebhook);
        this.service = service;
        this.property = property;
        this.chatroomService = chatroomService;
    }

    @PostConstruct
    @SneakyThrows
    public void initialize() {
        this.chatroomService.setAbsSender(this);
        this.chatroomService.setChatroomGroupId(this.execute(GetChat.builder()
                .chatId("@" + this.property.getSupergroupName())
                .build()).getId().toString());
    }


    @Override
    @SneakyThrows
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        log.info("Webhook received update[{}]", toJson(update));

        List<PartialBotApiMethod<Message>> messages = service.receive(this, update);
        messages.forEach(this::executeMessage);
        log.info("Webhook answer update[id:{}] messages size[{}] ", update.getUpdateId(), messages.size());

        return null;
    }


    @SneakyThrows
    public void executeMessage(PartialBotApiMethod<Message> message) {
        log.info("Webhook execute message[{}]", toJson(message));
        if (message instanceof SendMessage) {
            execute((SendMessage) message);
        }
        if (message instanceof SendPhoto) {
            execute((SendPhoto) message);
        }
    }


    @Override
    public String getBotPath() {
        return property.getPath();
    }

    @Override
    public String getBotUsername() {
        return property.getUsername();
    }

    @Override
    public String getBotToken() {
        return property.getToken();
    }

}
