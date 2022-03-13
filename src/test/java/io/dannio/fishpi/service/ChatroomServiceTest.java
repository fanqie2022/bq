package io.dannio.fishpi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.danniod.fish4j.entites.ChatroomMessage;
import io.github.danniod.fish4j.entites.chatroom.RedPacketMessage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static io.dannio.fishpi.util.JsonResource.fromResource;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
class ChatroomServiceTest {

    @Autowired
    private ChatroomService service;


    @Test
    @SneakyThrows
    void testRedPacket() {
        final ChatroomMessage message = new ObjectMapper().readValue(fromResource("fishpi/redPacket.json"), RedPacketMessage.class);
        service.messageToTelegram(message);
    }
}
