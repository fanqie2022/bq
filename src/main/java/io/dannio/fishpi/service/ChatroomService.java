package io.dannio.fishpi.service;

import io.github.danniod.fish4j.api.FishApi;
import io.github.danniod.fish4j.entites.ChatroomMessage;
import io.github.danniod.fish4j.entites.chatroom.*;
import io.github.danniod.fish4j.enums.ChatroomMessageType;
import io.github.danniod.fish4j.param.MessageParam;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatroomService {

    @Setter
    private String chatroomGroupId;

    @Setter
    private AbsSender absSender;

    private final FishApi fishApi;


    @SneakyThrows
    public void messageToTelegram(String text) {
        log.info("fishpi -> telegram message[{}]", text);

        absSender.execute(SendMessage.builder()
                .chatId(chatroomGroupId)
                .text(text)
//                .parseMode(ParseMode.HTML)
                .build());
    }


    public void messageToFishPi(Message message) {
        log.info("telegram -> fishpi message[{}]", message);
    }

}
