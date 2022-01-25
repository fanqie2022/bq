package io.dannio.fishpi.utils;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;

import java.io.IOException;

@FunctionalInterface
public interface FailureCallback<T extends BaseRequest<T, R>, R extends BaseResponse> {

    void onFailure(T request, IOException e);
}
