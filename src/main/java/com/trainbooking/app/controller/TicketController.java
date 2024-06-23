package com.trainbooking.app.controller;

import com.trainbooking.app.dto.*;
import com.trainbooking.app.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/train/{trainId}")
    public List<TicketDTO> getTicketsByTrainId(@PathVariable Long trainId) {
        return ticketService.getTicketsByTrainId(trainId);
    }

    @PostMapping("/book")
    public TicketDTO bookTicket(@RequestBody TicketRequest ticketRequest) throws Exception {
        return ticketService.bookTicket(
                ticketRequest.getFirstName(),
                ticketRequest.getLastName(),
                ticketRequest.getEmail(),
                ticketRequest.getTrainId(),
                ticketRequest.getSource(),
                ticketRequest.getDestination()
        );
    }

    @GetMapping("/receipt/{ticketId}")
    public TicketDTO getReceipt(@PathVariable Long ticketId) throws Exception {
        return ticketService.getReceipt(ticketId);
    }

    @PostMapping("/users/section")
    public List<UserSeatDTO> getUsersBySection(@RequestBody SectionRequest sectionRequest) {
        return ticketService.getUsersBySection(sectionRequest.getTrainId(), sectionRequest.getSection());
    }

    @DeleteMapping("/{ticketId}")
    public void removeUser(@PathVariable Long ticketId) throws Exception {
        ticketService.removeUser(ticketId);
    }

    @PostMapping("/modify-seat")
    public TicketDTO modifyUserSeat(@RequestBody ModifySeatRequest modifySeatRequest) throws Exception {
        return ticketService.modifyUserSeat(modifySeatRequest.getTicketId(), modifySeatRequest.getNewSection());
    }
}
