package com.trainbooking.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrainDTO {
    private Long id;
    private String name;
    private int capacity;

}
