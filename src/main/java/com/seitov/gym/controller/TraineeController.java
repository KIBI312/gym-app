package com.seitov.gym.controller;

import com.seitov.gym.dto.UserDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.service.TraineeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/trainee")
public class TraineeController {

    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UsernamePasswordDto registerTrainee(@RequestBody UserDto userDto) {
        return traineeService.createTrainee(userDto);
    }

}
