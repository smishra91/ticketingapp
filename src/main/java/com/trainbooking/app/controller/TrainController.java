package com.trainbooking.app.controller;

import com.trainbooking.app.dto.TrainDTO;
import com.trainbooking.app.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/trains")
public class TrainController {
    @Autowired
    private TrainService trainService;

    @GetMapping
    public List<TrainDTO> getAllTrains() {
        return trainService.getAllTrains();
    }

    @GetMapping("/{id}")
    public TrainDTO getTrainById(@PathVariable Long id) {
        return trainService.getTrainById(id);
    }
}
