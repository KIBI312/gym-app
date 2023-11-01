package com.seitov.gym.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.seitov.gym.dto.common.FullName;
import com.seitov.gym.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerShortDto {

    private String username;
    @JsonUnwrapped
    private FullName fullName;
    @NotNull(message = "Please provide trainer's specialization information!")
    private TrainingType.Name specialization;

}
