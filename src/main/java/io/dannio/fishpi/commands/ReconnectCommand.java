package io.dannio.fishpi.commands;

import io.dannio.fishpi.config.FishApiConfig;
import io.dannio.fishpi.service.ChatroomService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * This commands reconnect chatroom with the Bot
 *
 */
@Slf4j
@Component
public class ReconnectCommand extends BotCommand {

    private ChatroomService service;

    private FishApiConfig fishApiConfig;

    public ReconnectCommand(ChatroomService service) {
        super("reconnect", "Reconnect command you can reconnect chatroom with the Bot");

    }


    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        fishApiConfig.reconnectChatroom(service);

        absSender.execute(SendMessage.builder()
                .chatId(chat.getId().toString())
                .text("OK！check group [Chatroom](https://t.me/fishpi_cr)")
                .parseMode(ParseMode.MARKDOWN).build());
    }
}
