package com.seitov.gym.service;

import com.seitov.gym.dao.TraineeDao;
import com.seitov.gym.dto.UserDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private TraineeDao traineeDao;

    @InjectMocks
    private TraineeService traineeService;

    @Test
    public void traineeCreation() {
        //given
        UserDto userDto = new UserDto("John", "Smith",
                LocalDate.of(1975, 3, 15), "NY street");
        //when
        when(userService.generateUsername("John", "Smith")).thenReturn("John.Smith");
        when(userService.generatePassword()).thenReturn("1234567910");
        //then
        UsernamePasswordDto result = traineeService.createTrainee(userDto);
        assertEquals("John.Smith",result.getUsername());
        assertEquals(10, result.getPassword().length());
    }

    @Test
    public void traineeCreationWithExistingUsername() {
        //given
        UserDto userDto = new UserDto("John", "Smith",
                LocalDate.of(1975, 3, 15), "NY street");
        //when
        when(userService.generateUsername("John", "Smith")).thenReturn("John.Smith3");
        when(userService.generatePassword()).thenReturn("1234567910");
        //then
        UsernamePasswordDto result = traineeService.createTrainee(userDto);
        assertEquals("John.Smith3",result.getUsername());
        assertEquals(10, result.getPassword().length());
    }

}
