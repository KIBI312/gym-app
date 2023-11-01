package com.seitov.gym.controller;

import com.seitov.gym.dto.*;
import com.seitov.gym.service.TraineeService;
import com.seitov.gym.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping(path = "/api/trainer")
public class TrainerController {

    private final TrainerService trainerService;
    private final String AUTH_CHECK_BY_USERNAME = "#username == authentication.name";

    public TrainerController(TrainerService trainerService, TraineeService traineeService) {
        this.trainerService = trainerService;
    }

    @Operation(description = "Creates new Trainer profile", tags = "trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UsernamePasswordDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UsernamePasswordDto registerTrainer(@RequestBody TrainerShortDto trainerShortDto) {
        return trainerService.createTrainer(trainerShortDto);
    }

    @Operation(description = "Get Trainer profile", tags = "trainer")
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
    public TrainerDto getTrainerProfile(@PathVariable String username) {
        return trainerService.getTrainer(username);
    }

    @Operation(description = "Update Trainer profile", tags = "trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TrainerDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation errors",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Incorrect credentials or trying update Inactive profile",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PutMapping(path = "/{username}")
    @PreAuthorize(AUTH_CHECK_BY_USERNAME)
    public TrainerDto updateTrainer(@PathVariable String username, @RequestBody @Valid TrainerShortDto dto) {
        return trainerService.updateTrainer(username, dto);
    }

    @Operation(description = "Get active trainers not assigned on trainee", tags = "trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = TrainerShortDto.class)))),
            @ApiResponse(responseCode = "403", description = "Incorrect credentials",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @GetMapping(path = "/notAssignedTo/trainee/{username}")
    public List<TrainerShortDto> getFreeTrainers(@PathVariable String username) {
        return trainerService.getNotAssignedTrainers(username);
    }


}
