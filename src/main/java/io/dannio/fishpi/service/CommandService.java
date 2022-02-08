package io.dannio.fishpi.service;

import io.github.danniod.fish4j.api.FishApi;
import io.github.danniod.fish4j.entites.FishPiUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CommandService {

    private final FishApi fishApi;


    public FishPiUser linkFishAccount(String apiKey) {
        return fishApi.getUser(apiKey);
    }
}
