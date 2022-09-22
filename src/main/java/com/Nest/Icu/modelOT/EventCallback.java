package com.Nest.Icu.modelOT;

import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.MessageSentCallback;
import com.microsoft.azure.sdk.iot.device.exceptions.IotHubClientException;

public class EventCallback implements MessageSentCallback {

    @Override
    public void onMessageSent(Message sentMessage, IotHubClientException exception, Object callbackContext) {
        System.out.println("IoT Hub responded to message with status: "
                + (exception == null ? IotHubStatusCode.OK.toString() : exception.getStatusCode()));
        if (callbackContext != null) {
            synchronized (callbackContext) {
                callbackContext.notify();
            }
        }
    }

}