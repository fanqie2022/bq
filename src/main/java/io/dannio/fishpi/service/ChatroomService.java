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
    public void messageToTelegram(ChatroomMessage chatroomMessage) {

        switch (ChatroomMessageType.fromType(chatroomMessage.getType())) {

            case MSG:
                final ChatMessage chatMessage = (ChatMessage) chatroomMessage;

                absSender.execute(SendMessage.builder()
                        .chatId(chatroomGroupId)
                        .text(chatMessage.getMarkdownContent())
                        .parseMode(ParseMode.HTML)
                        .build());
                break;
            case ONLINE:
                final OnlineMessage onlineMessage = (OnlineMessage) chatroomMessage;

                break;
            case RED_PACKET:
                final RedPacketMessage redPacketMessage = (RedPacketMessage) chatroomMessage;

                break;
            case RED_PACKET_STATUS:
                final RedPacketStatusMessage redPacketStatusMessage = (RedPacketStatusMessage) chatroomMessage;

                break;
            case REVOKE:
                final RevokeMessage revokeMessage = (RevokeMessage) chatroomMessage;

                break;
            default:

        }

    }


    public void messageToFishPi(Message message) {
        log.info("telegram -> fishpi message[{}]", message);

        fishApi.sendMessage(MessageParam.builder()
                .apiKey("FgKt3UMtyNiimukgWBqYyzJp4VrUPKVd")
                .content(message.getText())
                .build());
    }

}
