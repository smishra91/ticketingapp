package com.trainbooking.app.service;

import com.trainbooking.app.dto.TrainDTO;

import java.util.List;

public interface TrainService {
    List<TrainDTO> getAllTrains();
    TrainDTO getTrainById(Long id);
}
