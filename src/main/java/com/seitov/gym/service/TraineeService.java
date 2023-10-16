package com.seitov.gym.service;

import com.seitov.gym.dto.UserDto;
import com.seitov.gym.dto.UsernamePasswordDto;

public interface TraineeService {

    UsernamePasswordDto createTrainee(UserDto dto);

}
