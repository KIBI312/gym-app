package com.seitov.gym.controller;

import com.seitov.gym.dto.ErrorMessage;
import com.seitov.gym.dto.TraineeDto;
import com.seitov.gym.dto.UserDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.service.TraineeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/trainee")
public class TraineeController {

    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Operation(description = "Creates new Trainee profile", tags = "trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                     schema = @Schema(implementation = UsernamePasswordDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UsernamePasswordDto registerTrainee(@RequestBody UserDto userDto) {
        return traineeService.createTrainee(userDto);
    }

    @Operation(description = "Get Trainee profile", tags = "trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TraineeDto.class))),
            @ApiResponse(responseCode = "400", description = "Requesting non-existing profile",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @GetMapping(path = "/{username}")
    public TraineeDto getTraineeProfile(@PathVariable String username) {
        return traineeService.getTrainee(username);
    }

}
