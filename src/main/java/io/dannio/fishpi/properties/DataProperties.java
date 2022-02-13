package io.dannio.fishpi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app.data")
public @Data
class DataProperties {

    private String telegram;

    private String fishpi;

}
