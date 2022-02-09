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
    public void messageToTelegram(ChatroomMessage message) {

        switch (ChatroomMessageType.fromType(message.getType())) {

            case MSG:
                final ChatMessage chatMessage = (ChatMessage) message;

                final String user = chatMessage.getUserNickname() != null
                        ? String.format("%s(%s)", chatMessage.getUserNickname(), chatMessage.getUserName())
                        : chatMessage.getUserName();

                final String content = String.format("%s:\n%s", user, chatMessage.getMarkdownContent());

                absSender.execute(SendMessage.builder()
                        .chatId(chatroomGroupId)
                        .text(content)
                        .parseMode(ParseMode.MARKDOWNV2)
                        .build());
                break;
            case ONLINE:
                final OnlineMessage onlineMessage = (OnlineMessage) message;

                break;
            case RED_PACKET:
                final RedPacketMessage redPacketMessage = (RedPacketMessage) message;

                break;
            case RED_PACKET_STATUS:
                final RedPacketStatusMessage redPacketStatusMessage = (RedPacketStatusMessage) message;

                break;
            case REVOKE:
                final RevokeMessage revokeMessage = (RevokeMessage) message;

                break;
            default:

        }

    }


    public void messageToFishPi(Message message) {
        log.info("telegram -> fishpi message[{}]", message.getText());

        if (message.hasText()) {
            fishApi.sendMessage(MessageParam.builder()
                    .apiKey("FgKt3UMtyNiimukgWBqYyzJp4VrUPKVd")
                    .content(message.getText())
                    .build());
        }
    }

}
