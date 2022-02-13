package io.dannio.fishpi.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
@ConfigurationProperties("app.data")
public @Data
class DataProperties {

    private String telegram;

    private String fishpi;


    @PostConstruct
    public void initialize() {
        if (!mkdirIfNotExists(new File(this.telegram)) && !mkdirIfNotExists(new File(this.fishpi))) {
            throw new RuntimeException("app data store can not initialize");
        }
    }


    private boolean mkdirIfNotExists(File file) {
        return file.exists() || file.mkdirs();
    }
}
