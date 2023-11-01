package com.seitov.gym.service;

import com.seitov.gym.dto.TrainingDto;

import java.util.List;

public interface TrainingService {

    void createTraining(TrainingDto dto);
    List<TrainingDto> getTraineeTrainings(String username);
    List<TrainingDto> getTrainerTrainings(String username);

}
