package io.dannio.fishpi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fish.api.FishApi;
import org.fish.entites.FishPiUser;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CommandService {

    private final FishApi fishApi;


    public FishPiUser LinkFishAccount(String apiKey) {
        return fishApi.getUser(apiKey);
    }
}
