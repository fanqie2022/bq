package io.dannio.fishpi;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication
public class BotApplication {


    public static void main(String[] args) {

        SpringApplication.run(BotApplication.class, args);
    }
}
