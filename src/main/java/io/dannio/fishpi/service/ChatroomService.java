package io.dannio.fishpi.service;

import io.dannio.fishpi.bot.FishpiBot;
import io.dannio.fishpi.properties.DataProperties;
import io.github.danniod.fish4j.api.FishApi;
import io.github.danniod.fish4j.entites.ChatroomMessage;
import io.github.danniod.fish4j.entites.Storage;
import io.github.danniod.fish4j.entites.chatroom.*;
import io.github.danniod.fish4j.enums.ChatroomMessageType;
import io.github.danniod.fish4j.param.MessageParam;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static io.dannio.fishpi.util.FileUtils.convert2Gif;
import static io.dannio.fishpi.util.FileUtils.downloadFromTelegram;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatroomService {

    @Setter
    private String chatroomGroupId;

    @Setter
    private AbsSender absSender;

    private final FishApi fishApi;

    private final DataProperties dataProperties;


    @SneakyThrows
    public void messageToTelegram(ChatroomMessage message) {

        switch (ChatroomMessageType.fromType(message.getType())) {

            case MSG:
                final ChatMessage chatMessage = (ChatMessage) message;

                final String user = StringUtils.isNotBlank(chatMessage.getUserNickname())
                        ? String.format("%s(%s)", chatMessage.getUserNickname(), chatMessage.getUserName())
                        : chatMessage.getUserName();

                final String content = String.format("%s:\n%s", user, chatMessage.getMarkdownContent());
                log.info("-> telegram msg[{}]", content);

                absSender.execute(SendMessage.builder()
                        .chatId(chatroomGroupId)
                        .text(content)
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


    @SneakyThrows
    public void messageToFishPi(Message message) {

        if (message.hasText()) {
            sendMessage(message.getText());
        }

        if (message.hasAnimation()) {
            final Animation animation = message.getAnimation();
            final File source = absSender.execute(GetFile.builder().fileId(animation.getFileId()).build());
            final String filePath = source.getFilePath();
            final String fileUrl = source.getFileUrl(((FishpiBot) absSender).getBotToken());
            java.io.File videoFile = downloadFromTelegram(fileUrl, dataProperties.getTelegram() + java.io.File.separator + filePath);
            final String gifFile = dataProperties.getFishpi() + java.io.File.separator + filePath.replaceAll("\\.mp4", ".gif");
            convert2Gif(videoFile.getAbsolutePath(), gifFile, progress -> {
                log.info("process status[{}], isEnd[{}]", progress.status, progress.isEnd());
                if (progress.isEnd()) {
                    try {
                        log.info("upload fileName[{}]", gifFile);
                        final java.io.File file = new java.io.File(gifFile);
                        final Storage upload = fishApi.upload(file);
                        log.info("upload file[{}], result[{}]", file.getAbsolutePath(), upload);
                        final String picUrl = upload.getSuccessMap().get(file.getName());
                        if (picUrl != null) {
                            sendMessage(String.format("![%s](%s)", file.getName(), picUrl));
                        } else {
                            log.warn("upload to fishpi failure, cannot get picture url, and upload result is [{}]", upload);
                        }
                    } catch (Exception e) {
                        log.error("error occurred", e);
                    }
                }
            });
        }
    }


    private void sendMessage(String content) {
        log.info("telegram -> fishpi message[{}]", content);

        fishApi.sendMessage(MessageParam.builder()
                .apiKey("lRIEfO4Iqvn9fAhMxEHr9o6Ee15Aw3RQ")
                .content(content)
                .build());
    }

}
