package com.trainbooking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private double price;
    private Long trainId;
    private String section;
    private String source;
    private String destination;

}
