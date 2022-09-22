package com.Nest.Icu.modelIOT;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
//Copyright (c) Microsoft. All rights reserved.
//Licensed under the MIT license. See LICENSE file in the project root for full license information.
//This application uses the Azure IoT Hub device SDK for Java
//For samples see: https://github.com/Azure/azure-iot-sdk-java/tree/master/device/iot-device-samples
import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.MessageSentCallback;
import com.microsoft.azure.sdk.iot.device.exceptions.IotHubClientException;

@Service
public class SimulatedDevice {
    // The device connection string to authenticate the device with your IoT hub.
    private static String connString = "HostName=team4-IOThub.azure-devices.net;DeviceId=HeartRateSensor;SharedAccessKey=CffYYGNMyNAmVOgjl5iQWiZ7E0lmC/NGhhnH7wNWOyk=";

    // Using the MQTT protocol to connect to IoT Hub
    private static IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
    private static DeviceClient client;

    // Specify the T e l e m e t r y to send to your IoT hub.
    private static class TelemetryDataPoint {

        public int heartbeat;
        public int systolePressure;
        public int diastolePressure;

        // Serialize object to JSON format.
        public String serialize() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }
    }

    // Print the acknowledgement received from IoT Hub for the telemetry message
    // sent.
    private static class EventCallback implements MessageSentCallback {
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

    @Service
    private static class MessageSender implements Runnable {

        String appmessage = " ";

        public void run() {
            try {
                // Initialize the simulated telemetry.
                int minHB = 60;
                int maxHB = 85;
                int maxSP = 140;
                int minSP = 100;
                int maxDP = 100;
                int minDP = 60;
                Random rand = new Random();

                while (true) {
                    // Simulate telemetry
                    double currentHB = minHB + rand.nextDouble() * (maxHB - minHB + 1);
                    double currentSP = minSP + rand.nextDouble() * (maxSP - minSP + 1);
                    double currentDP = minDP + rand.nextDouble() * (maxDP - minDP + 1);

                    TelemetryDataPoint telemetryDataPoint = new TelemetryDataPoint();
                    telemetryDataPoint.heartbeat = (int) currentHB;
                    telemetryDataPoint.systolePressure = (int) currentSP;
                    telemetryDataPoint.diastolePressure = (int) currentDP;

                    // Add the telemetry to the message body as JSON.
                    String msgStr = telemetryDataPoint.serialize();
                    Message msg = new Message(msgStr);

                    // Add a custom application property to the message.
                    // An IoT hub can filter on these properties without access to the message body.

                    msg.setProperty("heartBeatAlert", (currentHB > 82 || currentHB < 64) ? "true" : "false");
                    msg.setProperty("bloodPressureAlert",
                            (currentSP > 135 || currentSP < 105 || currentDP > 95 || currentDP < 65) ? "true"
                                    : "false");
                    msg.setProperty("heartbeat", Integer.toString(telemetryDataPoint.heartbeat));
                    msg.setProperty("systolepressure", Integer.toString(telemetryDataPoint.systolePressure));
                    msg.setProperty("diastolepressure", Integer.toString(telemetryDataPoint.diastolePressure));
                    //////////////
                    appmessage = msgStr;
                    // System.out.println("after message: " + appmessage );

                    Object lockobj = new Object();

                    // Send the message.
                    EventCallback callback = new EventCallback();
                    client.sendEventAsync(msg, callback, lockobj);

                    synchronized (lockobj) {
                        lockobj.wait();
                    }
                    Thread.sleep(1000);

                }
            } catch (InterruptedException e) {
                System.out.println("Finished.");
            }
        }
    }

    public void iotdataGeneration() throws IOException, URISyntaxException, IotHubClientException {

        // Connect to the IoT hub.
        client = new DeviceClient(connString, protocol);

        // Open a connection from the device client.
        // Boolean parameter configures retry behavior (false => no retry).
        client.open(false);

        // Create new thread and start sending messages
        MessageSender sender = new MessageSender();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(sender);

        // Stop the application.
        System.out.println("Press ENTER to exit.");
        System.in.read();
        executor.shutdownNow();
        client.close();
    }

    public void getHeartData() throws InterruptedException {

        while (true) {
            MessageSender sender = new MessageSender();
            System.out.println(sender.appmessage);
            Thread.sleep(1000);
            // return sender.appmessage;
        }

    }

}
