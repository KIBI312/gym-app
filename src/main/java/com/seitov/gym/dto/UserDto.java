package com.seitov.gym.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    @Size(min = 2, max = 20, message = "First name should be in range between 2 and 20 characters")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 20, message = "Last name should be in range between 2 and 20 characters")
    private String lastName;
    @Schema(type = "string", pattern = "dd-MM-yyyy", example = "31-12-2022")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private String address;

}
