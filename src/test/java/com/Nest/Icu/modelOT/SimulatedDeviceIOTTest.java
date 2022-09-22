package com.Nest.Icu.modelOT;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.Nest.Icu.exceptions.PatientNotFoundException;
import com.Nest.Icu.repository.PatientRepository;
import com.microsoft.azure.sdk.iot.device.exceptions.IotHubClientException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SimulatedDeviceIOT.class})
@ExtendWith(SpringExtension.class)
class SimulatedDeviceIOTTest {
    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private SimulatedDeviceIOT simulatedDeviceIOT;


    /**
     * Method under test: {@link SimulatedDeviceIOT#iotdataGeneration(UUID)}
     */
    @Test
    void testIotdataGeneration2()
            throws PatientNotFoundException, IotHubClientException, IOException, URISyntaxException {
        when(patientRepository.existsById((UUID) any())).thenReturn(false);
        assertThrows(PatientNotFoundException.class, () -> simulatedDeviceIOT.iotdataGeneration(UUID.randomUUID()));
        verify(patientRepository).existsById((UUID) any());
    }
}

