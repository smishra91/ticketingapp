package com.trainbooking.app.service.impl;

import com.trainbooking.app.dto.TicketDTO;
import com.trainbooking.app.dto.UserSeatDTO;
import com.trainbooking.app.exception.TicketNotFoundException;
import com.trainbooking.app.exception.TrainNotFoundException;
import com.trainbooking.app.model.Ticket;
import com.trainbooking.app.model.Train;
import com.trainbooking.app.repository.TicketRepository;
import com.trainbooking.app.repository.TrainRepository;
import com.trainbooking.app.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Override
    public List<TicketDTO> getTicketsByTrainId(Long trainId) {
        return ticketRepository.findByTrainId(trainId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TicketDTO bookTicket(String firstName, String lastName, String email, Long trainId, String source, String destination) throws Exception {
        Train train = trainRepository.findByIdWithLock(trainId)
                .orElseThrow(() -> new TrainNotFoundException("Train not found"));

        String section;
        if (train.getAvailableSeatsSectionA() > 0) {
            train.setAvailableSeatsSectionA(train.getAvailableSeatsSectionA() - 1);
            section = "A";
        } else if (train.getAvailableSeatsSectionB() > 0) {
            train.setAvailableSeatsSectionB(train.getAvailableSeatsSectionB() - 1);
            section = "B";
        } else {
            throw new Exception("No seats available in the requested section");
        }

        Ticket ticket = new Ticket();
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        ticket.setEmail(email);
        ticket.setTrain(train);
        ticket.setSection(section);
        ticket.setSource(source);
        ticket.setDestination(destination);
        ticketRepository.save(ticket);
        return convertToDTO(ticket);

    }

    @Override
    public TicketDTO getReceipt(Long ticketId) throws Exception {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
        return convertToDTO(ticket);
    }

    @Override
    public List<UserSeatDTO> getUsersBySection(Long trainId, String section) {
        return ticketRepository.findByTrainIdAndSection(trainId, section).stream()
                .map(ticket -> new UserSeatDTO(
                        ticket.getFirstName(),
                        ticket.getLastName(),
                        ticket.getEmail(),
                        ticket.getSection(),
                        (long) ticket.getId()  // Assuming seat number is the ticket ID for simplicity
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void removeUser(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
        ticketRepository.delete(ticket);
    }

    @Override
    @Transactional
    public TicketDTO modifyUserSeat(Long ticketId, String newSection) throws Exception {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        Train train = ticket.getTrain();

        // Allocate the new seat
        if (newSection.equalsIgnoreCase("A") && train.getAvailableSeatsSectionA() > 0) {
            train.setAvailableSeatsSectionA(train.getAvailableSeatsSectionA() - 1);
            train.setAvailableSeatsSectionB(train.getAvailableSeatsSectionB() + 1);
        } else if (newSection.equalsIgnoreCase("B") && train.getAvailableSeatsSectionB() > 0) {
            train.setAvailableSeatsSectionB(train.getAvailableSeatsSectionB() - 1);
            train.setAvailableSeatsSectionA(train.getAvailableSeatsSectionA() + 1);
        } else {
            throw new Exception("No seats available in the requested section");
        }

        ticket.setSection(newSection);
        ticketRepository.save(ticket);
        return convertToDTO(ticket);
    }


    private TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setFirstName(ticket.getFirstName());
        dto.setLastName(ticket.getLastName());
        dto.setEmail(ticket.getEmail());
        dto.setPrice(ticket.getPrice());
        dto.setTrainId(ticket.getTrain().getId());
        dto.setSource(ticket.getSource());
        dto.setDestination(ticket.getDestination());
        dto.setSection(ticket.getSection());
        return dto;
    }
}
