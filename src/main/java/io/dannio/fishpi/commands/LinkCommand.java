package io.dannio.fishpi.commands;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * This commands link the account of fishpi site with the bot
 *
 */
@Slf4j
@Component
public class LinkCommand extends BotCommand {


    public LinkCommand() {
        super("link", "link to your fishpi account.");
}


    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText("hello world!");
        absSender.execute(answer);
    }
}