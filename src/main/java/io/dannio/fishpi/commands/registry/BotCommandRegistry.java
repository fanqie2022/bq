package io.dannio.fishpi.commands.registry;

import io.dannio.fishpi.commands.StartCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

@AllArgsConstructor
@Component
public class BotCommandRegistry implements ICommandRegistry {

    private final CommandRegistry commandRegistry;

    private final StartCommand startCommand;

    @PostConstruct
    public void registerCommands() {
        this.commandRegistry.register(startCommand);
    }


    public boolean executeCommand(AbsSender absSender, Message message) {
        return commandRegistry.executeCommand(absSender, message);
    }


    @Override
    public void registerDefaultAction(BiConsumer<AbsSender, Message> defaultConsumer) {
        commandRegistry.registerDefaultAction(defaultConsumer);
    }

    @Override
    public final boolean register(IBotCommand botCommand) {
        return commandRegistry.register(botCommand);
    }

    @Override
    public final Map<IBotCommand, Boolean> registerAll(IBotCommand... botCommands) {
        return commandRegistry.registerAll(botCommands);
    }

    @Override
    public final boolean deregister(IBotCommand botCommand) {
        return commandRegistry.deregister(botCommand);
    }

    @Override
    public final Map<IBotCommand, Boolean> deregisterAll(IBotCommand... botCommands) {
        return commandRegistry.deregisterAll(botCommands);
    }

    @Override
    public final Collection<IBotCommand> getRegisteredCommands() {
        return commandRegistry.getRegisteredCommands();
    }

    @Override
    public final IBotCommand getRegisteredCommand(String commandIdentifier) {
        return commandRegistry.getRegisteredCommand(commandIdentifier);
    }
}
