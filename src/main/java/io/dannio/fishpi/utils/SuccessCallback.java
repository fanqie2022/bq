package io.dannio.fishpi.utils;

import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;

@FunctionalInterface
public interface SuccessCallback<T extends BaseRequest<T, R>, R extends BaseResponse> {

    void onResponse(T request, R response);
}
