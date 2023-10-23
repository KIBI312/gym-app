package com.seitov.gym.service;

import com.seitov.gym.dto.*;
import com.seitov.gym.dto.common.PersonalInfo;

import java.util.List;

public interface TraineeService {

    UsernamePasswordDto createTrainee(PersonalInfo personalInfo);
    TraineeDto getTrainee(String username);
    List<TrainerDto> addTrainers(String traineeUsername, List<String> usernames);
    TraineeDto updateTrainee(String username, PersonalInfo personalInfo);
    void deleteTrainee(UsernamePasswordDto dto);

}
