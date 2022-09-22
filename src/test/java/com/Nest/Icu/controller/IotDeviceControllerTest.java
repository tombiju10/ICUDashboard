package com.Nest.Icu.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.Nest.Icu.model.PatientHistory;
import com.Nest.Icu.modelIOT.SimulatedDevice;
import com.Nest.Icu.modelOT.SimulatedDeviceIOT;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {IotDeviceController.class})
@ExtendWith(SpringExtension.class)
class IotDeviceControllerTest {
    @Autowired
    private IotDeviceController iotDeviceController;

    @MockBean
    private SimulatedDevice simulatedDevice;

    @MockBean
    private SimulatedDeviceIOT simulatedDeviceIOT;

    /**
     * Method under test: {@link IotDeviceController#post(PatientHistory, UUID)}
     */
    @Test
    void testPost() throws Exception {
        when(simulatedDeviceIOT.iotdataGeneration((UUID) any())).thenReturn("Iotdata Generation");

        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setDate("2020-03-01");
        patientHistory.setDiastolepressure(1);
        patientHistory.setHeartbeat(1);
        patientHistory.setSLNO(1);
        patientHistory.setSystolePressure(1);
        String content = (new ObjectMapper()).writeValueAsString(patientHistory);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/icu/bed/iot/livedata/{patientid}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(iotDeviceController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Iotdata Generation"));
    }
}

