package io.dannio.fishpi.config;

import io.dannio.fishpi.service.ChatroomService;
import io.github.danniod.fish4j.api.FishApi;
import io.github.danniod.fish4j.api.FishApiImpl;
import io.github.danniod.fish4j.client.WebSocketClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class FishApiConfig {

    private static final int[] RECONNECT_DELAYS = {10000, 60000, 180000};
    private int reconnectTimes = 0;

    @Bean
    public FishApi fishApi(Retrofit retrofit) {
        return new FishApiImpl(retrofit);
    }


    @Bean
    public Retrofit retrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl("https://fishpi.cn/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }


    @Bean
    public WebSocket webSocket(OkHttpClient client, WebSocketClient webSocketClient) {
        Request request = new Request.Builder()
                .url("https://fishpi.cn/chat-room-channel?apiKey=FgKt3UMtyNiimukgWBqYyzJp4VrUPKVd")
                .build();
        return client.newWebSocket(request, webSocketClient);
    }


    @Bean
    public WebSocketClient webSocketClient(ChatroomService service) {
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("ws-heartbeat-%d").daemon(true).build());

        return new WebSocketClient((webSocket, response) -> executor.scheduleAtFixedRate(() -> {
            webSocket.send("-hb-");
            log.info("Chatroom websocket heartbeat");
        }, 0, 3, TimeUnit.MINUTES), (webSocket, i, s) -> {
            webSocket.close(i, s);
            reconnect(executor, service);
        }, (webSocket, throwable, response) -> {
            log.warn("websocket broken. onFailure", throwable);
            reconnect(executor, service);
        }, (webSocket, message) -> service.messageToTelegram(message));
    }


    @SneakyThrows
    private void reconnect(ScheduledExecutorService executor, ChatroomService service) {
        executor.shutdown();
        if (reconnectTimes >= RECONNECT_DELAYS.length) {
            return;
        }
        Thread.sleep(RECONNECT_DELAYS[reconnectTimes++]);
        webSocketClient(service);
    }


    @Bean
    public OkHttpClient customUaClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> chain.proceed(chain.request()
                        .newBuilder()
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.99 Safari/537.36")
                        .build())
                ).build();
    }


}
