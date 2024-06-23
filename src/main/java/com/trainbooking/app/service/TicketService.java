package com.trainbooking.app.service;

import com.trainbooking.app.dto.TicketDTO;
import com.trainbooking.app.dto.UserSeatDTO;

import java.util.List;

public interface TicketService {
    List<TicketDTO> getTicketsByTrainId(Long trainId);
    TicketDTO bookTicket(String firstName, String lastName, String email, Long trainId, String source, String destination) throws Exception;
    TicketDTO getReceipt(Long ticketId) throws Exception;
    List<UserSeatDTO> getUsersBySection(Long trainId, String section);

    void removeUser(Long ticketId);

    TicketDTO modifyUserSeat(Long ticketId, String newSection) throws Exception;
}
