package com.seitov.gym.service;

import com.seitov.gym.dto.TrainerDto;
import com.seitov.gym.dto.UsernamePasswordDto;

public interface TrainerService {

    UsernamePasswordDto createTrainer(TrainerDto dto);

}
