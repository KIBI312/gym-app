package com.seitov.gym.controller;

import com.seitov.gym.dto.TrainerDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.service.TrainerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/trainer")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UsernamePasswordDto registerTrainer(@RequestBody TrainerDto trainerDto) {
        return trainerService.createTrainer(trainerDto);
    }

}
