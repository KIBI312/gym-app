package com.seitov.gym.service;

import com.seitov.gym.dto.TrainingDto;
import com.seitov.gym.entity.Training;

import java.util.List;

public interface TrainingService {

    Training createTraining(TrainingDto dto);
    List<TrainingDto> getTraineeTrainings(String username);
    List<TrainingDto> getTrainerTrainings(String username);

}
