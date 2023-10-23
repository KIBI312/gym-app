package com.seitov.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignTrainersDto {

    @NotEmpty(message = "'trainers' array must not be empty!")
    private List<String> trainers;

}
