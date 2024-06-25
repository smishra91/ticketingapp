package com.trainbooking.app.controller;

import com.trainbooking.app.dto.TrainDTO;
import com.trainbooking.app.service.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.trainbooking.app.TestUtils.generateRandomObject;
import static com.trainbooking.app.TestUtils.generateRandomObjectList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TrainControllerTest {
    @Mock
    private TrainService trainService;

    @InjectMocks
    private TrainController trainController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainController).build();
    }

    @Test
    void testGetAllTrains() throws Exception {
        List<TrainDTO> trains = generateRandomObjectList(TrainDTO.class, 2);
        when(trainService.getAllTrains()).thenReturn(trains);

        mockMvc.perform(get("/trains"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(trains.get(0).getName())));
    }

    @Test
    void testGetTrainById() throws Exception {
        TrainDTO train = generateRandomObject(TrainDTO.class);

        when(trainService.getTrainById(anyLong())).thenReturn(train);

        mockMvc.perform(get("/trains/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(train.getName())));
    }
}
