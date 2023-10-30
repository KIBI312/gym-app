package com.seitov.gym.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.seitov.gym.dto.common.FullName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeShortDto {

    private String username;
    @JsonUnwrapped
    private FullName fullName;

}
