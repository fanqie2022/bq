package io.dannio.fishpi.commands;

import io.dannio.fishpi.commands.registry.BotCommandRegistry;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * This command helps the user to find the command they need
 *
 */
@Slf4j
@Component
public class HelpCommand extends BotCommand {

    public static final String COMMAND_IDENTIFIER = "help";

    @Setter
    private BotCommandRegistry commandRegistry;


    public HelpCommand() {
        super(COMMAND_IDENTIFIER, "Get all the commands this bot provides");
    }


    @SneakyThrows
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        StringBuilder helpMessageBuilder = new StringBuilder("<b>Help</b>\n");
        helpMessageBuilder.append("These are the registered commands for this Bot:\n\n");

        for (IBotCommand botCommand : commandRegistry.getRegisteredCommands()) {
            if (botCommand instanceof StopCommand) {
                continue;
            }
            helpMessageBuilder.append(botCommand.toString()).append("\n\n");
        }

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        absSender.execute(helpMessage);
    }
}
