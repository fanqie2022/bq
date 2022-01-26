package io.dannio.fishpi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FishpiService {


    @SneakyThrows
    public List<PartialBotApiMethod<Message>> receive(Update update) {

        List<PartialBotApiMethod<Message>> messages = new ArrayList<>();
        if (!update.hasMessage()) {
            return messages;
        }
        final String text = update.getMessage().getText();
        if (text.startsWith("/")) {

        }

        return messages;
    }
}
