package com.Nest.Icu.modelOT;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.exceptions.IotHubClientException;
import org.junit.jupiter.api.Test;

class EventCallbackTest {
    /**
     * Method under test: {@link EventCallback#onMessageSent(Message, IotHubClientException, Object)}
     */
    @Test
    void testOnMessageSent() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by onMessageSent(Message, IotHubClientException, Object)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        EventCallback eventCallback = new EventCallback();
        Message sentMessage = new Message();
        eventCallback.onMessageSent(sentMessage, new IotHubClientException(IotHubStatusCode.OK), "Callback Context");
    }

    /**
     * Method under test: {@link EventCallback#onMessageSent(Message, IotHubClientException, Object)}
     */
    @Test
    void testOnMessageSent2() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by onMessageSent(Message, IotHubClientException, Object)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        EventCallback eventCallback = new EventCallback();
        eventCallback.onMessageSent(new Message(), null, null);
    }

    /**
     * Method under test: {@link EventCallback#onMessageSent(Message, IotHubClientException, Object)}
     */
    @Test
    void testOnMessageSent3() {
        EventCallback eventCallback = new EventCallback();
        Message sentMessage = new Message();
        IotHubClientException iotHubClientException = mock(IotHubClientException.class);
        when(iotHubClientException.getStatusCode()).thenReturn(IotHubStatusCode.OK);
        eventCallback.onMessageSent(sentMessage, iotHubClientException, "Callback Context");
        verify(iotHubClientException).getStatusCode();
    }
}

