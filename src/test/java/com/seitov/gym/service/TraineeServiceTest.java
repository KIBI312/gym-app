package com.seitov.gym.service;

import com.seitov.gym.dao.TraineeDao;
import com.seitov.gym.dto.TraineeDto;
import com.seitov.gym.dto.UserDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.User;
import com.seitov.gym.service.impl.TraineeServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private PasswordService passwordService;
    @Mock
    private TraineeDao traineeDao;
    @Mock
    private MapperFacade orikaMapper;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private final Trainee trainee = new Trainee();
    private final TraineeDto traineeDto = new TraineeDto();

    @BeforeEach
    public void initData() {
        //Trainee init
        User user = new User();
        user.setUsername("John.Smith");
        user.setFirstName("John");
        user.setLastName("Smith");
        trainee.setUser(user);
        trainee.setAddress("NY Street");
        trainee.setDateOfBirth(LocalDate.of(1975, 3, 15));
        //TraineeDto init
        traineeDto.setFirstName(trainee.getUser().getFirstName());
        traineeDto.setLastName(trainee.getUser().getLastName());
        traineeDto.setDateOfBirth(trainee.getDateOfBirth());
        traineeDto.setAddress(trainee.getAddress());
        traineeDto.setIsActive(trainee.getUser().getIsActive());
        traineeDto.setTrainers(Collections.emptyList());
    }

    @Test
    public void traineeCreation() {
        //given
        UserDto userDto = new UserDto("John", "Smith",
                LocalDate.of(1975, 3, 15), "NY street");
        //when
        when(userService.generateUsername("John", "Smith")).thenReturn("John.Smith");
        when(passwordService.generatePassword()).thenReturn("1234567910");
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
        when(passwordService.generatePassword()).thenReturn("1234567910");
        //then
        UsernamePasswordDto result = traineeService.createTrainee(userDto);
        assertEquals("John.Smith3",result.getUsername());
        assertEquals(10, result.getPassword().length());
    }

    @Test
    public void getTraineeProfile() {
        //given
        String username = "John.Smith";
        //when
        when(traineeDao.findByUsername(username)).thenReturn(Optional.of(trainee));
        //then
        assertEquals(traineeDto, traineeService.getTrainee(username));
    }

    @Test
    public void getTraineeProfileWithNonExistingUsername() {
        //given
        String username = "Vasiliy.Smith";
        //when
        when(traineeDao.findByUsername(any())).thenReturn(Optional.empty());
        //then
        Exception ex = assertThrows(UsernameNotFoundException.class, () -> traineeService.getTrainee(username));
        assertEquals("User with username: Vasiliy.Smith doesn't exist", ex.getMessage());
    }

}
