package io.dannio.fishpi.utils;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;

import java.io.IOException;

public class CallbackUtils {

    public static <T extends BaseRequest<T, R>, R extends BaseResponse> Callback<T, R> getCallback(SuccessCallback<T, R> successCallback,
                                                                                                   FailureCallback<T, R> failureCallback) {
        return new Callback<>() {
            @Override
            public void onResponse(T request, R response) {
                successCallback.onResponse(request, response);
            }

            @Override
            public void onFailure(T request, IOException e) {
                failureCallback.onFailure(request, e);
            }
        };
    }
}
