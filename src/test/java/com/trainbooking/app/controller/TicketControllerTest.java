package com.trainbooking.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainbooking.app.dto.*;
import com.trainbooking.app.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.trainbooking.app.TestUtils.*;
import static com.trainbooking.app.TestUtils.generateRandomObject;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TicketControllerTest {
    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetTicketsByTrainId() throws Exception {
        List<TicketDTO> tickets = generateRandomObjectList(TicketDTO.class,2);
        when(ticketService.getTicketsByTrainId(anyLong())).thenReturn(tickets);

        mockMvc.perform(get("/tickets/train/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(tickets.get(0).getFirstName())));
    }

    @Test
    void testBookTicket() throws Exception {
        TicketRequest request =  generateRandomObject(TicketRequest.class);
        TicketDTO response = generateRandomObject(TicketDTO.class);
        when(ticketService.bookTicket(anyString(), anyString(), anyString(), anyLong(), anyString(), anyString())).thenReturn(response);

        mockMvc.perform(post("/tickets/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(response.getFirstName())));
    }

    @Test
    void testGetReceipt() throws Exception {
        TicketDTO response = generateRandomObject(TicketDTO.class);
        when(ticketService.getReceipt(anyLong())).thenReturn(response);

        mockMvc.perform(get("/tickets/receipt/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(response.getFirstName())));
    }

    @Test
    void testGetUsersBySection() throws Exception {
        List<UserSeatDTO> users = generateRandomObjectList(UserSeatDTO.class,3);
        SectionRequest sectionRequest = generateRandomObject(SectionRequest.class);
        when(ticketService.getUsersBySection(anyLong(), anyString())).thenReturn(users);

        mockMvc.perform(post("/tickets/users/section")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sectionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].firstName", is(users.get(0).getFirstName())));
    }

    @Test
    void testRemoveUser() throws Exception {
        mockMvc.perform(delete("/tickets/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyUserSeat() throws Exception {
        ModifySeatRequest request = generateRandomObject(ModifySeatRequest.class);
        TicketDTO response = generateRandomObject(TicketDTO.class);
        when(ticketService.modifyUserSeat(anyLong(), anyString())).thenReturn(response);

        mockMvc.perform(post("/tickets/modify-seat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.section", is(response.getSection())));
    }
}
