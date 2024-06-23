package com.trainbooking.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Long trainId;
    private String source;
    private String destination;
}
