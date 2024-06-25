package com.trainbooking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Long trainId;
    private String source;
    private String destination;
}
