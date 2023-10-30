package com.seitov.gym.controller;

import com.seitov.gym.dto.*;
import com.seitov.gym.dto.common.PersonalInfo;
import com.seitov.gym.service.TraineeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/trainee")
public class TraineeController {

    private final TraineeService traineeService;
    private final String AUTH_CHECK_BY_USERNAME = "#username == authentication.name";

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
    public UsernamePasswordDto registerTrainee(@RequestBody PersonalInfo personalInfo) {
        return traineeService.createTrainee(personalInfo);
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
    @PreAuthorize(AUTH_CHECK_BY_USERNAME)
    public TraineeDto getTraineeProfile(@PathVariable String username) {
        return traineeService.getTrainee(username);
    }

    @Operation(description = "Update Trainee profile", tags = "trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TraineeDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Incorrect credentials or trying update Inactive profile",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PutMapping(path = "/{username}")
    @PreAuthorize(AUTH_CHECK_BY_USERNAME)
    public TraineeDto updateTrainee(@PathVariable String username, @RequestBody @Valid PersonalInfo dto) {
        return traineeService.updateTrainee(username, dto);
    }

    @Operation(description = "Delete Trainee profile", tags = "trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "400", description = "Trying to delete non-existing profile",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @DeleteMapping(path = "/{username}")
    @PreAuthorize(AUTH_CHECK_BY_USERNAME)
    public void deleteTrainee(@PathVariable String username) {
        traineeService.deleteTrainee(username);
    }

    @Operation(description = "Assign new Trainers to Trainee", tags = "trainee")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", content =
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = TraineeDto.class))),
                @ApiResponse(responseCode = "400", description = "Trying to add Trainers to non-existing Trainee",
                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = ErrorMessage.class))),
        })
    @PutMapping(path = "/{username}/trainers")
    @PreAuthorize(AUTH_CHECK_BY_USERNAME)
    public List<TrainerShortDto> assignTrainers(@PathVariable String username, @RequestBody @Valid AssignTrainersDto dto) {
        return traineeService.addTrainers(username, dto.getTrainers());
    }

}
