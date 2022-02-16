package io.dannio.fishpi.bot;

import io.dannio.fishpi.properties.BotProperties;
import io.dannio.fishpi.service.ChatroomService;
import io.dannio.fishpi.service.FishpiService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import javax.annotation.PostConstruct;

import static io.dannio.fishpi.util.JsonUtils.toJson;

@Slf4j
@Component
public class FishpiBot extends SpringWebhookBot {

    private final FishpiService service;

    private final ChatroomService chatroomService;

    private final BotProperties properties;


    public FishpiBot(SetWebhook setWebhook, FishpiService service, ChatroomService chatroomService, BotProperties properties) {
        super(setWebhook);
        this.service = service;
        this.properties = properties;
        this.chatroomService = chatroomService;
    }

    @PostConstruct
    @SneakyThrows
    public void initialize() {
        this.chatroomService.setAbsSender(this);
        this.chatroomService.setChatroomGroupId(this.properties.getSupergroupName() == null
                ? this.properties.getSupergroupId()
                : this.execute(GetChat.builder()
                .chatId("@" + this.properties.getSupergroupName())
                .build()).getId().toString());
    }


    @Override
    @SneakyThrows
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        log.info("Webhook received update[{}]", toJson(update));

        service.receive(this, update);

        return null;
    }


    public String getFileUrl(String filePath) {
        return File.getFileUrl(this.getBotPath(), filePath);
    }

    @Override
    public String getBotPath() {
        return properties.getPath();
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

}
