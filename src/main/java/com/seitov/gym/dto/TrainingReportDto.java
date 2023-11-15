package com.seitov.gym.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.seitov.gym.dto.common.FullName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingReportDto {

    private String trainerUsername;
    @JsonUnwrapped
    private FullName fullName;
    private Boolean isActive;
    @Schema(type = "string", pattern = "dd-MM-yyyy", example = "31-12-2022")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    private Number duration;
    private ActionType actionType;

    public enum ActionType {
        ADD, DELETE
    }

}
