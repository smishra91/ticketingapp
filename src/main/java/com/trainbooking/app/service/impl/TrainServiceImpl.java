package com.trainbooking.app.service.impl;

import com.trainbooking.app.dto.TrainDTO;
import com.trainbooking.app.model.Train;
import com.trainbooking.app.repository.TrainRepository;
import com.trainbooking.app.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import com.trainbooking.app.exception.TrainNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainRepository trainRepository;

    @Override
    public List<TrainDTO> getAllTrains() {
        return trainRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TrainDTO getTrainById(Long id) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new TrainNotFoundException("Train not found"));
        return convertToDTO(train);
    }

    private TrainDTO convertToDTO(Train train) {
        TrainDTO dto = new TrainDTO();
        dto.setId(train.getId());
        dto.setName(train.getName());
        dto.setCapacity(train.getSectionACapacity()+ train.getSectionBCapacity());
        return dto;
    }
}
