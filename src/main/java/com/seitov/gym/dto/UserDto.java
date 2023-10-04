package com.seitov.gym.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserDto {

    @NotBlank
    @Size(min = 2, max = 20, message = "First name should be in range between 2 and 20 characters")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 20, message = "Last name should be in range between 2 and 20 characters")
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;

}
