package com.seitov.gym.controller;

import com.seitov.gym.dto.ErrorMessage;
import com.seitov.gym.dto.TrainingDto;
import com.seitov.gym.dto.TrainingReportDto;
import com.seitov.gym.entity.Training;
import com.seitov.gym.service.ReportService;
import com.seitov.gym.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

    private final TrainingService trainingService;
    private final ReportService reportService;

    public TrainingController(TrainingService trainingService, ReportService reportService) {
        this.trainingService = trainingService;
        this.reportService = reportService;
    }

    @Operation(description = "Creates new Training", tags = "training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = TrainingDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addTraining(@RequestBody @Valid TrainingDto dto) {
        Training training = trainingService.createTraining(dto);
        reportService.reportTraining(training, TrainingReportDto.ActionType.ADD);
    }

    @Operation(description = "Get trainer trainings list", tags = "training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = TrainingDto.class)))),
            @ApiResponse(responseCode = "403", description = "Incorrect credentials",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @GetMapping(path = "/trainer/{username}")
    public List<TrainingDto> getTrainerTrainings(@PathVariable String username) {
        return trainingService.getTrainerTrainings(username);
    }

    @Operation(description = "Get trainee trainings list", tags = "training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = TrainingDto.class)))),
            @ApiResponse(responseCode = "403", description = "Incorrect credentials",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @GetMapping(path = "/trainee/{username}")
    public List<TrainingDto> getTraineeTrainings(@PathVariable String username) {
        return trainingService.getTraineeTrainings(username);
    }

}
