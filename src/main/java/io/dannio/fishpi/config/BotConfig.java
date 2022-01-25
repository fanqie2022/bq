package io.dannio.fishpi.config;

import io.dannio.fishpi.bot.FishpiBot;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Slf4j
@Configuration
public class BotConfig {

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url("https://fishpi-bot.herokuapp.com").build();
    }

    @Bean
    @SneakyThrows
    public FishpiBot fishpiBot(SetWebhook setWebhookInstance) {

        FishpiBot fishpiBot = new FishpiBot(setWebhookInstance);
        DefaultWebhook defaultWebhook = new DefaultWebhook();

        defaultWebhook.setInternalUrl(
                "http://localhost:8080");
        // the port to start the server, on the localhost computer, on the server it
        // be the server address
        //   defaultWebhook.registerWebhook(fishpiBot);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, defaultWebhook);

        log.info("SetWebHook from fishpi bot {}", setWebhookInstance);
        fishpiBot.getBotUsername();
        telegramBotsApi.registerBot(fishpiBot, setWebhookInstance);
        return fishpiBot;

    }
}
