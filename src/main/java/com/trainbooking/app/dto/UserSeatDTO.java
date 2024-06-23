package com.trainbooking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSeatDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String section;
    private Long seatNumber;
}
