package com.trainbooking.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainDTO {
    private Long id;
    private String name;
    private int capacity;
}
