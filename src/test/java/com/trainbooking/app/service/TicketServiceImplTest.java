package com.trainbooking.app.service;

import com.trainbooking.app.dto.TicketDTO;
import com.trainbooking.app.dto.UserSeatDTO;
import com.trainbooking.app.exception.TicketNotFoundException;
import com.trainbooking.app.model.Ticket;
import com.trainbooking.app.model.Train;
import com.trainbooking.app.repository.TicketRepository;
import com.trainbooking.app.repository.TrainRepository;
import com.trainbooking.app.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.trainbooking.app.TestUtils.generateRandomObject;
import static com.trainbooking.app.TestUtils.generateRandomObjectList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TrainRepository trainRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTicketsByTrainId() {
        List<Ticket> tickets = generateRandomObjectList(Ticket.class,2);
        when(ticketRepository.findByTrainId(anyLong())).thenReturn(tickets);

        List<TicketDTO> result = ticketService.getTicketsByTrainId(1L);
        assertEquals(2, result.size());
        assertEquals(tickets.get(0).getFirstName(), result.get(0).getFirstName());
    }

    @Test
    void testBookTicket() throws Exception {
        Train train = generateRandomObject(Train.class);

        when(trainRepository.findByIdWithLock(anyLong())).thenReturn(Optional.of(train));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArgument(0));

        TicketDTO result = ticketService.bookTicket("John", "Doe", "john.doe@example.com", 1L, "CityA", "CityB");
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertNotNull(result.getSection());
        assertEquals(train.getId(),result.getTrainId());
    }

    @Test
    void testBookTicketNoSeatsAvailable() {
        Train train = generateRandomObject(Train.class);
        train.setAvailableSeatsSectionA(0);
        train.setAvailableSeatsSectionB(0);

        when(trainRepository.findByIdWithLock(anyLong())).thenReturn(Optional.of(train));

        Exception exception = assertThrows(Exception.class, () -> {
            ticketService.bookTicket("John", "Doe", "john.doe@example.com", 1L, "CityA", "CityB");
        });

        assertEquals("No seats available in the requested section", exception.getMessage());
    }

    @Test
    void testGetReceipt() throws Exception {
        Ticket ticket = generateRandomObject(Ticket.class);
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));

        TicketDTO result = ticketService.getReceipt(1L);
        assertNotNull(result);
        assertEquals(ticket.getFirstName(), result.getFirstName());
    }

    @Test
    void testGetReceiptTicketNotFound() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.getReceipt(1L);
        });

        assertEquals("Ticket not found", exception.getMessage());
    }

    @Test
    void testGetUsersBySection() {
        List<Ticket> tickets = generateRandomObjectList(Ticket.class,4);
        when(ticketRepository.findByTrainIdAndSection(anyLong(), anyString())).thenReturn(tickets);

        List<UserSeatDTO> result = ticketService.getUsersBySection(1L, "A");
        assertEquals(4, result.size());
        assertEquals(tickets.get(0).getFirstName(), result.get(0).getFirstName());
    }

    @Test
    void testRemoveUser() {
        Ticket ticket = generateRandomObject(Ticket.class);
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
        doNothing().when(ticketRepository).delete(any(Ticket.class));

        ticketService.removeUser(ticket.getId());
        verify(ticketRepository, times(1)).delete(ticket);
    }

    @Test
    void testRemoveUserTicketNotFound() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(TicketNotFoundException.class, () -> {
            ticketService.removeUser(1L);
        });

        assertEquals("Ticket not found", exception.getMessage());
    }

    @Test
    void testModifyUserSeat() throws Exception {
        Train train = generateRandomObject(Train.class);
        train.setAvailableSeatsSectionA(10);
        train.setAvailableSeatsSectionB(5);
        int seatsInA = train.getAvailableSeatsSectionA();
        int seatsInb = train.getAvailableSeatsSectionB();

        Ticket ticket = new Ticket(1L, "John", "Doe", "john.doe@example.com", 50.0, "B", "CityA", "CityB", train);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArgument(0));

        TicketDTO result = ticketService.modifyUserSeat(1L, "A");
        assertNotNull(result);
        assertEquals("A", result.getSection());
        assertEquals(seatsInA-1, train.getAvailableSeatsSectionA());
        assertEquals(seatsInb+1, train.getAvailableSeatsSectionB());
    }

    @Test
    void testModifyUserSeatNoSeatsAvailable() {
        Train train = new Train();
        train.setId(1L);
        train.setAvailableSeatsSectionA(0);
        train.setAvailableSeatsSectionB(5);

        Ticket ticket = new Ticket(1L, "John", "Doe", "john.doe@example.com", 50.0, "B", "CityA", "CityB", train);

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));

        Exception exception = assertThrows(Exception.class, () -> {
            ticketService.modifyUserSeat(1L, "A");
        });

        assertEquals("No seats available in the requested section", exception.getMessage());
    }
}

