package io.dannio.fishpi;

import io.dannio.fishpi.bot.FishpiBot;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Slf4j
@Configuration
public class TelegramBotConfig {


    @SneakyThrows
    @Bean
    public TelegramBotsApi initTelegramBot() {
        DefaultBotOptions botOptions = new DefaultBotOptions();

        DefaultWebhook webhook = new DefaultWebhook();
        DefaultBotSession defaultBotSession = new DefaultBotSession();
        defaultBotSession.setOptions(botOptions);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(defaultBotSession.getClass(), webhook);
        telegramBotsApi.registerBot(new FishpiBot(), new SetWebhook());
        return telegramBotsApi;
    }

}