package com.seitov.gym.service;

import com.seitov.gym.dto.TraineeDto;
import com.seitov.gym.dto.TrainerDto;
import com.seitov.gym.dto.UserDto;
import com.seitov.gym.dto.UsernamePasswordDto;

import java.util.List;

public interface TraineeService {

    UsernamePasswordDto createTrainee(UserDto dto);
    TraineeDto getTrainee(String username);
    List<TrainerDto> addTrainers(String traineeUsername, List<String> usernames);

}
