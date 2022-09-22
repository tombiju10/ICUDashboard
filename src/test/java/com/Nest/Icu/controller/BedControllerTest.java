package com.Nest.Icu.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.Nest.Icu.exceptions.BedAlreadyExistsException;
import com.Nest.Icu.exceptions.BedNotFoundException;
import com.Nest.Icu.model.Bed;
import com.Nest.Icu.model.BedStatus;
import com.Nest.Icu.service.BedService;
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

@ContextConfiguration(classes = {BedController.class})
@ExtendWith(SpringExtension.class)
class BedControllerTest {
    @Autowired
    private BedController bedController;

    @MockBean
    private BedService bedService;


    /**
     * Method under test: {@link BedController#updateBed(Bed, UUID)}
     */
    @Test
    void testUpdateBed() throws Exception {
        when(bedService.updateBed((Bed) any(), (UUID) any())).thenThrow(new BedNotFoundException("?"));

        Bed bed = new Bed();
        bed.setBedId(UUID.randomUUID());
        bed.setBedStatus(BedStatus.Available);
        bed.setBloodPressureId("42");
        bed.setHeartBeatId("42");
        bed.setName("Name");
        bed.setPatient(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(bed);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/icu/bed/update/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(bedController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link BedController#deleteBed(UUID)}
     */
    @Test
    void testDeleteBed() throws Exception {
        doNothing().when(bedService).deleteBed((UUID) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/icu/bed/delete/{id}",
                UUID.randomUUID());
        MockMvcBuilders.standaloneSetup(bedController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("deleted"));
    }


    /**
     * Method under test: {@link BedController#getBedList()}
     */
    @Test
    void testGetBedList() throws Exception {
        when(bedService.getBedsList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/icu/bed/list");
        MockMvcBuilders.standaloneSetup(bedController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test: {@link BedController#getBedList()}
     */
    @Test
    void testGetBedList2() throws Exception {
        when(bedService.getBedsList()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/icu/bed/list");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(bedController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/xml;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("<List/>"));
    }

    /**
     * Method under test: {@link BedController#postBed(Bed)}
     */
    @Test
    void testPostBed() throws Exception {
        when(bedService.addBed((Bed) any())).thenThrow(new BedAlreadyExistsException("?"));

        Bed bed = new Bed();
        bed.setBedId(UUID.randomUUID());
        bed.setBedStatus(BedStatus.Available);
        bed.setBloodPressureId("42");
        bed.setHeartBeatId("42");
        bed.setName("Name");
        bed.setPatient(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(bed);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/icu/bed/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bedController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

