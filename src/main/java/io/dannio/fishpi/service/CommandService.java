package io.dannio.fishpi.service;

import io.dannio.fishpi.entity.TelegramUser;
import io.dannio.fishpi.repository.UserRepository;
import io.github.danniod.fish4j.api.FishApi;
import io.github.danniod.fish4j.entites.FishPiUser;
import io.github.danniod.fish4j.exception.FishApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.IOException;

@Slf4j
@AllArgsConstructor
@Service
public class CommandService {

    private final FishApi fishApi;

    private final UserRepository repository;

    public FishPiUser linkFishAccount(User user, String apiKey) {
        TelegramUser telegramUser = repository.getByTelegramId(user.getId());
        if (telegramUser == null) {
            telegramUser = new TelegramUser();
            telegramUser.setTelegramId(user.getId());
        }
        FishPiUser fishPiUser = null;
        try {
            fishPiUser = fishApi.getUser(apiKey);
            telegramUser.setApiKey(apiKey);
            telegramUser.setFishId(fishPiUser.getOid());
            repository.save(telegramUser);
        } catch (FishApiException | IOException e) {
            log.warn("error occurred when get api user by key.", e);
        }
        return fishPiUser;
    }
}
