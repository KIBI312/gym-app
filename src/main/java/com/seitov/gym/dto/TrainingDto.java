package com.seitov.gym.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDto {

    @NotBlank(message = "Please provide trainee username as traineeUsername field")
    private String traineeUsername;
    @NotBlank(message = "Please provide trainer username as trainerUsername field")
    private String trainerUsername;
    @NotBlank(message = "Please provide training name")
    private String name;
    @NotNull(message = "Please provide training date")
    @Schema(type = "string", pattern = "dd-MM-yyyy", example = "31-12-2022")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    @NotNull(message = "Please provide training duration")
    private Number duration;

}
