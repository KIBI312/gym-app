package com.seitov.gym.service;

import com.seitov.gym.dto.TrainerDto;
import com.seitov.gym.dto.TrainerShortDto;
import com.seitov.gym.dto.UsernamePasswordDto;

public interface TrainerService {

    UsernamePasswordDto createTrainer(TrainerShortDto dto);
    TrainerDto getTrainer(String username);
    TrainerDto updateTrainer(String username, TrainerShortDto dto);

}
