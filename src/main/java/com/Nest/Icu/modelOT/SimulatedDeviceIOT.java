package com.Nest.Icu.modelOT;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Nest.Icu.exceptions.PatientNotFoundException;
import com.Nest.Icu.model.Patient;
import com.Nest.Icu.model.PatientHistory;
import com.Nest.Icu.repository.PatientRepository;
//Copyright (c) Microsoft. All rights reserved.
//Licensed under the MIT license. See LICENSE file in the project root for full license information.
//This application uses the Azure IoT Hub device SDK for Java
//For samples see: https://github.com/Azure/azure-iot-sdk-java/tree/master/device/iot-device-samples
import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.azure.sdk.iot.device.exceptions.IotHubClientException;

import lombok.Data;

@Service
@Data
public class SimulatedDeviceIOT {

    // The device connection string to authenticate the device with your IoT hub.
    private static String connString = "HostName=team4-IOThub.azure-devices.net;DeviceId=HeartRateSensor;SharedAccessKey=CffYYGNMyNAmVOgjl5iQWiZ7E0lmC/NGhhnH7wNWOyk=";

    // Using the MQTT protocol to connect to IoT Hub
    private static IotHubClientProtocol protocol = IotHubClientProtocol.MQTT;
    private static DeviceClient client;
    // private UUID pid = UUID.fromString("c01c1081-8dcf-42f8-b7e2-e04ac483b923");

    @Autowired
    private PatientRepository patientRepository;



    public String iotdataGeneration(UUID patientid)
            throws IOException, URISyntaxException, IotHubClientException, PatientNotFoundException {

        if (!patientRepository.existsById(patientid)) {
            throw new PatientNotFoundException("Patiet not found with that id");
        } else {

            // Connect to the IoT hub.
            client = new DeviceClient(connString, protocol);

            // Open a connection from the device client.
            // Boolean parameter configures retry behavior (false => no retry).
            client.open(false);

            // Create new thread and start sending messages
            // MessageSender sender = new MessageSender();
            ExecutorService executor = Executors.newFixedThreadPool(1);
            executor.execute(sender(patientid));

            // Stop the application.
            System.out.println("Press ENTER to exit.");
            System.in.read();
            executor.shutdownNow();
            client.close();
        }
        return null;

    }

    private Runnable sender(UUID patientid) {

        try {
            int minHB = 60;
            int maxHB = 85;
            int maxSP = 140;
            int minSP = 100;
            int maxDP = 100;
            int minDP = 60;
            Random rand = new Random();

            while (true) {

                double currentHB = minHB + rand.nextDouble() * (maxHB - minHB + 1);
                double currentSP = minSP + rand.nextDouble() * (maxSP - minSP + 1);
                double currentDP = minDP + rand.nextDouble() * (maxDP - minDP + 1);
                TelemetryDataPoint telemetryDataPoint = new TelemetryDataPoint();
                telemetryDataPoint.heartbeat = (int) currentHB;
                telemetryDataPoint.systolePressure = (int) currentSP;
                telemetryDataPoint.diastolePressure = (int) currentDP;

                int HB = (int) currentHB;
                int DP = (int) currentDP;
                int SP = (int) currentSP;
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

                PatientHistory patientHistory = new PatientHistory();
                patientHistory.setHeartbeat(HB);
                patientHistory.setDiastolepressure(DP);
                patientHistory.setSystolePressure(SP);
                patientHistory.setDate(dtf.format(now));
                Patient patient = patientRepository.findById(patientid).get();
                patient.getPatientHistory().add(patientHistory);
                patientRepository.save(patient);
                System.out.println(patient);
                patient.getPatientHistory().get(patient.getPatientHistory().size() - 1);

                // Add the telemetry to the message body as JSON.
                String msgStr = telemetryDataPoint.serialize();
                Message msg = new Message(msgStr);
                // Add a custom application property to the message.
                // An IoT hub can filter on these properties without access to the message body.
                msg.setProperty("heartBeatAlert", (currentHB > 82 || currentHB < 64) ? "true" : "false");
                msg.setProperty("bloodPressureAlert",
                        (currentSP > 135 || currentSP < 105 || currentDP > 95 || currentDP < 65) ? "true" : "false");
                msg.setProperty("heartbeat", Integer.toString(telemetryDataPoint.heartbeat));
                msg.setProperty("systolepressure", Integer.toString(telemetryDataPoint.systolePressure));
                msg.setProperty("diastolepressure", Integer.toString(telemetryDataPoint.diastolePressure));

                System.out.println("Sending message: " + msgStr);
                /*
                 *
                 */
                // appmessage = msgStr;
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

        return null;
    }

}
