package com.seitov.gym.service;

import com.seitov.gym.dto.*;

import java.util.List;

public interface TraineeService {

    UsernamePasswordDto createTrainee(UserDto dto);
    TraineeDto getTrainee(String username);
    List<TrainerDto> addTrainers(String traineeUsername, List<String> usernames);
    TraineeDto updateTrainee(UpdateTraineeDto dto);
    void deleteTrainee(UsernamePasswordDto dto);

}
