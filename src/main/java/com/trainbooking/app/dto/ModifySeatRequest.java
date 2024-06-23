package com.trainbooking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifySeatRequest {
    private Long ticketId;
    private String newSection;
}
