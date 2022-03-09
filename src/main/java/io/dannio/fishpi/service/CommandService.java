package io.dannio.fishpi.service;

import io.dannio.fishpi.entity.TelegramUser;
import io.dannio.fishpi.repository.UserRepository;
import io.github.danniod.fish4j.api.FishApi;
import io.github.danniod.fish4j.entites.FishPiUser;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
@AllArgsConstructor
@Service
public class CommandService {

    private final FishApi fishApi;

    private final UserRepository repository;

    @SneakyThrows
    public FishPiUser linkFishAccount(User user, String apiKey) {
        TelegramUser telegramUser = repository.getByTelegramId(user.getId());
        if (telegramUser == null) {
            telegramUser = new TelegramUser();
            telegramUser.setTelegramId(user.getId());
        }
        FishPiUser fishPiUser = fishApi.getUser(apiKey);
        telegramUser.setApiKey(apiKey);
        telegramUser.setFishId(fishPiUser.getOid());
        repository.save(telegramUser);
        return fishPiUser;
    }


    @SneakyThrows
    public boolean pingFishpi(User user) {
        final TelegramUser telegramUser = repository.getByTelegramId(user.getId());
        return telegramUser != null && fishApi.getUser(telegramUser.getApiKey()) != null;
    }
}
