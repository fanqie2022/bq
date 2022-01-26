package io.dannio.fishpi.config;

import io.dannio.fishpi.bot.FishpiBot;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Slf4j
@Configuration
public class BotConfig {


    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.path}")
    private String botPath;

    @Value("${bot.webhook-url}")
    private String webhookUrl;

    @Value("${server.port}")
    private Integer port;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(this.webhookUrl).build();
    }

    @Bean
    @SneakyThrows
    public FishpiBot fishpiBot(SetWebhook setWebhookInstance) {

        FishpiBot fishpiBot = new FishpiBot(setWebhookInstance, botUsername, botToken, botPath);
        DefaultWebhook defaultWebhook = new DefaultWebhook();

        // the port to start the server, on the localhost computer, on the server it be the server address
        defaultWebhook.setInternalUrl("http://localhost:" + this.port);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class, defaultWebhook);

        telegramBotsApi.registerBot(fishpiBot, setWebhookInstance);
        log.info("register bot[{}] with SetWebHook[{}]", fishpiBot.getBotUsername(), setWebhookInstance);

        return fishpiBot;
    }
}
