package com.Nest.Icu.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Nest.Icu.exceptions.PatientNotFoundException;
import com.Nest.Icu.model.PatientHistory;
import com.Nest.Icu.modelIOT.SimulatedDevice;
import com.Nest.Icu.modelOT.SimulatedDeviceIOT;
import com.microsoft.azure.sdk.iot.device.exceptions.IotHubClientException;

@RestController
@RequestMapping("/icu/bed/iot")
@CrossOrigin(origins = "*")
public class IotDeviceController {

    @Autowired
    SimulatedDevice simulatedDevice;

    @Autowired
    SimulatedDeviceIOT simulatedDeviceIOT;

    @PostMapping("/livedata/{patientid}")
    public ResponseEntity<?> post(@RequestBody PatientHistory patientHistory,
                                  @PathVariable(value = "patientid") UUID patientid)
            throws IOException, URISyntaxException, IotHubClientException, PatientNotFoundException {
        ResponseEntity<?> response;

        response = new ResponseEntity<>(simulatedDeviceIOT.iotdataGeneration(patientid), HttpStatus.OK);

        return response;
    }

}
