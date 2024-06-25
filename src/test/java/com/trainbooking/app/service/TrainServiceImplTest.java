package com.trainbooking.app.service;

import com.trainbooking.app.dto.TrainDTO;
import com.trainbooking.app.exception.TrainNotFoundException;
import com.trainbooking.app.model.Train;
import com.trainbooking.app.repository.TrainRepository;
import com.trainbooking.app.service.impl.TrainServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.trainbooking.app.TestUtils.generateRandomObject;
import static com.trainbooking.app.TestUtils.generateRandomObjectList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TrainServiceImplTest {

    @Mock
    private TrainRepository trainRepository;

    @InjectMocks
    private TrainServiceImpl trainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTrains() {
        List<Train> trains = generateRandomObjectList(Train.class, 3);
        when(trainRepository.findAll()).thenReturn(trains);

        List<TrainDTO> result = trainService.getAllTrains();
        assertEquals(3, result.size());
        assertEquals(trains.get(0).getName(), result.get(0).getName());
        assertEquals(trains.get(0).getSectionACapacity() + trains.get(0).getSectionBCapacity(), result.get(0).getCapacity());
    }

    @Test
    void testGetTrainById() {
        Train train = generateRandomObject(Train.class);
        when(trainRepository.findById(anyLong())).thenReturn(Optional.of(train));

        TrainDTO result = trainService.getTrainById(1L);
        assertNotNull(result);
        assertEquals(train.getName(), result.getName());
    }

    @Test
    void testGetTrainByIdNotFound() {
        when(trainRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(TrainNotFoundException.class, () -> {
            trainService.getTrainById(1L);
        });

        assertEquals("Train not found", exception.getMessage());
    }
}
