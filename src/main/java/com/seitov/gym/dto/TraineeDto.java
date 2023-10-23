package com.seitov.gym.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.seitov.gym.dto.common.PersonalInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeDto {

    @JsonUnwrapped
    private PersonalInfo personalInfo;
    private Boolean isActive;
    private List<TrainerDto> trainers;

}
