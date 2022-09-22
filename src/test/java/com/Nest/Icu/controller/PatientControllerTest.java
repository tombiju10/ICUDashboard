package com.Nest.Icu.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.Nest.Icu.controller.dto.PatientDto;
import com.Nest.Icu.exceptions.BedNotFoundException;
import com.Nest.Icu.exceptions.PatientNotFoundException;
import com.Nest.Icu.model.PatientHistory;
import com.Nest.Icu.model.PatientStatus;
import com.Nest.Icu.service.DeviceService;
import com.Nest.Icu.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PatientController.class})
@ExtendWith(SpringExtension.class)
class PatientControllerTest {
    @MockBean
    private DeviceService deviceService;

    @Autowired
    private PatientController patientController;

    @MockBean
    private PatientService patientService;


    /**
     * Method under test: {@link PatientController#getPatientList()}
     */
    @Test
    void testGetPatientList() throws Exception {
        when(patientService.getPatientList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/icu/bed/patient/list/patient");
        MockMvcBuilders.standaloneSetup(patientController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test: {@link PatientController#getPatientList()}
     */
    @Test
    void testGetPatientList2() throws Exception {
        when(patientService.getPatientList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/icu/bed/patient/list/patient");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(patientController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test: {@link PatientController#updatePatient(PatientDto, UUID)}
     */
    @Test
    void testUpdatePatient() throws Exception {
        when(patientService.updatePatient((PatientDto) any(), (UUID) any())).thenThrow(new PatientNotFoundException("?"));

        PatientDto patientDto = new PatientDto();
        patientDto.setAge(1);
        patientDto.setGender("Gender");
        patientDto.setName("Name");
        patientDto.setPatientStatus(PatientStatus.OnBed);
        patientDto.setPatientid(UUID.randomUUID());
        String content = (new ObjectMapper()).writeValueAsString(patientDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/icu/bed/patient/{patientId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(patientController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link PatientController#deletePatient(UUID)}
     */
    @Test
    void testDeletePatient() throws Exception {
        doNothing().when(patientService).deletePatient((UUID) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/icu/bed/patient/delete/{patientId}",
                UUID.randomUUID());
        MockMvcBuilders.standaloneSetup(patientController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("deleted"));
    }

    /**
     * Method under test: {@link PatientController#getLastData(UUID)}
     */
    @Test
    void testGetLastData() throws Exception {
        PatientHistory patientHistory = new PatientHistory();
        patientHistory.setDate("2020-03-01");
        patientHistory.setDiastolepressure(1);
        patientHistory.setHeartbeat(1);
        patientHistory.setSLNO(1);
        patientHistory.setSystolePressure(1);
        when(patientService.getLastData((UUID) any())).thenReturn(patientHistory);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/icu/bed/patient/last/{patientId}",
                UUID.randomUUID());
        MockMvcBuilders.standaloneSetup(patientController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "<PatientHistory><heartbeat>1</heartbeat><diastolepressure>1</diastolepressure><date>2020-03-01</date"
                                        + "><slno>1</slno><systolePressure>1</systolePressure></PatientHistory>"));
    }

    /**
     * Method under test: {@link PatientController#postPatient(PatientDto, UUID)}
     */
    @Test
    void testPostPatient() throws Exception {
        when(patientService.addPatient((PatientDto) any(), (UUID) any())).thenThrow(new BedNotFoundException("?"));

        PatientDto patientDto = new PatientDto();
        patientDto.setAge(1);
        patientDto.setGender("Gender");
        patientDto.setName("Name");
        patientDto.setPatientStatus(PatientStatus.OnBed);
        patientDto.setPatientid(UUID.randomUUID());
        String content = (new ObjectMapper()).writeValueAsString(patientDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/icu/bed/patient/add/{bedid}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(patientController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link PatientController#viewPatientLiveData(UUID)}
     */
    @Test
    void testViewPatientLiveData() throws Exception {
        when(patientService.getPatientHistory((UUID) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/icu/bed/patient/history/{patientId}",
                UUID.randomUUID());
        MockMvcBuilders.standaloneSetup(patientController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<ArrayList/>"));
    }
}

