package com.seitov.gym.dto;

import com.seitov.gym.entity.TrainingType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TrainerDto {

    @NotBlank
    @Size(min = 2, max = 20, message = "First name should be in range between 2 and 20 characters")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 20, message = "Last name should be in range between 2 and 20 characters")
    private String lastName;
    private TrainingType.Name specialization;


}
