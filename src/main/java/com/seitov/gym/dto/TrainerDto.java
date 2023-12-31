package com.seitov.gym.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.seitov.gym.dto.common.FullName;
import com.seitov.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainerDto {

    @JsonUnwrapped
    private FullName fullName;
    private TrainingType.Name specialization;
    private Boolean isActive;
    private List<TraineeShortDto> trainees;


}
