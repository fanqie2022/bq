package io.dannio.fishpi.service;

import io.dannio.fishpi.bot.FishpiBot;
import io.dannio.fishpi.commands.registry.BotCommandRegistry;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class FishpiService {

    private final BotCommandRegistry registry;


    @SneakyThrows
    public List<PartialBotApiMethod<Message>> receive(FishpiBot bot, Update update) {

        List<PartialBotApiMethod<Message>> answers = new ArrayList<>();
        Message message = update.getMessage();

        if (message.isCommand()) {
            if (!registry.executeCommand(bot, message)) {
                //we have received a not registered command, handle it as invalid
                answers.add(answerMessage(message.getChatId(), "Unrecognized command. Say what?"));
            }
        } else {
            answers.add(answerMessage(message.getChatId(), "hello"));
        }

        return answers;
    }


    SendMessage answerMessage(Long chatId, String message) {
        final SendMessage answer = new SendMessage();
        answer.setChatId(String.valueOf(chatId));
        answer.setText(message);
        return answer;
    }
}
