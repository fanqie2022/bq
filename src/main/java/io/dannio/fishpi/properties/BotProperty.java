package io.dannio.fishpi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("bot")
public @Data class BotProperty {

    private final String username;

    private final String token;

    private final String path;

}
