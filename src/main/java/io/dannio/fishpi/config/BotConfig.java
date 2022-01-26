package io.dannio.fishpi.config;

import io.dannio.fishpi.bot.FishpiBot;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Slf4j
@Configuration
public class BotConfig {


    @Value("${bot.webhook-url}")
    private String webhookUrl;

    @Value("${server.port}")
    private Integer port;

    @Bean
    public SetWebhook setWebhook() {
        return SetWebhook.builder().url(this.webhookUrl).build();
    }

    @Bean
    public DefaultWebhook defaultWebhook() {

        DefaultWebhook defaultWebhook = new DefaultWebhook();
        // the port to start the server, on the localhost computer, on the server it be the server address
        defaultWebhook.setInternalUrl("http://0.0.0.0:" + this.port);

        return defaultWebhook;
    }


    @Bean
    @SneakyThrows
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi(DefaultBotSession.class, defaultWebhook());
    }
}
