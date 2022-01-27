package io.dannio.fishpi.config;

import io.dannio.fishpi.properties.BotProperty;
import io.dannio.fishpi.properties.WebhookProperty;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Slf4j
@AllArgsConstructor
@Configuration
public class BotConfig {

    private final WebhookProperty webhookProperty;

    private final BotProperty botProperty;


    @Bean
    public CommandRegistry commandRegistry() {
        return new CommandRegistry(true, botProperty::getPath);
    }


    @Bean
    public SetWebhook setWebhook() {
        return SetWebhook.builder().url(webhookProperty.getUrl()).build();
    }

    @Bean
    public DefaultWebhook defaultWebhook() {

        DefaultWebhook defaultWebhook = new DefaultWebhook();
        // the port to start the server, on the localhost computer, on the server it be the server address
        defaultWebhook.setInternalUrl("http://0.0.0.0:" + webhookProperty.getPort());

        return defaultWebhook;
    }


    @Bean
    @SneakyThrows
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi(DefaultBotSession.class, defaultWebhook());
    }
}
