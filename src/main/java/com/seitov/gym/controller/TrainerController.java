package com.seitov.gym.controller;

import com.seitov.gym.dto.*;
import com.seitov.gym.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/trainer")
public class TrainerController {

    private final TrainerService trainerService;
    private final String AUTH_CHECK_BY_USERNAME = "#username == authentication.name";

    public TrainerController(TrainerService trainerService) {
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
    public TrainerDto getTraineeProfile(@PathVariable String username) {
        return trainerService.getTrainer(username);
    }

}
