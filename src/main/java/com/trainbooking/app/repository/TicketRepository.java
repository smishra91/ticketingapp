package com.trainbooking.app.repository;


import com.trainbooking.app.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByTrainId(Long trainId);

    List<Ticket> findByTrainIdAndSection(Long trainId, String section);
}
