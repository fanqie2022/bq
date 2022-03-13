package io.dannio.fishpi.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Configuration
public class JunitTestConfig {

    @Bean
    @SneakyThrows
    public TelegramBotsApi telegramBotsApi(DefaultWebhook webhook) {
        return new TelegramBotsApi(DefaultBotSession.class, webhook) {
            @Override
            public void registerBot(WebhookBot bot, SetWebhook setWebhook) {
                System.out.println("cancel register bot in junit test");
            }
        };
    }

}
