package com.seitov.gym.service;

import com.seitov.gym.dao.TraineeDao;
import com.seitov.gym.dto.TraineeDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.dto.common.FullName;
import com.seitov.gym.dto.common.PersonalInfo;
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
class TraineeServiceTest {

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
    void initData() {
        //Trainee init
        User user = new User();
        user.setUsername("John.Smith");
        user.setFirstName("John");
        user.setLastName("Smith");
        trainee.setUser(user);
        trainee.setAddress("NY Street");
        trainee.setDateOfBirth(LocalDate.of(1975, 3, 15));
        //TraineeDto init
        FullName fullName = new FullName(trainee.getUser().getFirstName(), trainee.getUser().getLastName());
        PersonalInfo personalInfo = new PersonalInfo(fullName, trainee.getDateOfBirth(), trainee.getAddress());
        traineeDto.setPersonalInfo(personalInfo);
        traineeDto.setIsActive(trainee.getUser().getIsActive());
        traineeDto.setTrainers(Collections.emptyList());
    }

    @Test
    void traineeCreation() {
        //given
        FullName fullName = new FullName("John", "Smith");
        PersonalInfo userDto = new PersonalInfo(fullName,
                LocalDate.of(1975, 3, 15), "NY street");
        //when
        when(userService.generateUsername("John", "Smith")).thenReturn("John.Smith");
        when(passwordService.generatePassword()).thenReturn("1234567910");
        when(orikaMapper.map(userDto, Trainee.class)).thenReturn(trainee);
        //then
        UsernamePasswordDto result = traineeService.createTrainee(userDto);
        assertEquals("John.Smith",result.getUsername());
        assertEquals(10, result.getPassword().length());
    }

    @Test
    void traineeCreationWithExistingUsername() {
        //given
        FullName fullName = new FullName("John", "Smith");
        PersonalInfo userDto = new PersonalInfo(fullName,
                LocalDate.of(1975, 3, 15), "NY street");
        //when
        when(userService.generateUsername("John", "Smith")).thenReturn("John.Smith3");
        when(passwordService.generatePassword()).thenReturn("1234567910");
        when(orikaMapper.map(userDto, Trainee.class)).thenReturn(trainee);
        //then
        UsernamePasswordDto result = traineeService.createTrainee(userDto);
        assertEquals("John.Smith3",result.getUsername());
        assertEquals(10, result.getPassword().length());
    }

    @Test
    void getTraineeProfile() {
        //given
        String username = "John.Smith";
        //when
        when(traineeDao.findByUsername(username)).thenReturn(Optional.of(trainee));
        when(orikaMapper.map(trainee, TraineeDto.class)).thenReturn(traineeDto);
        //then
        assertEquals(traineeDto, traineeService.getTrainee(username));
    }

    @Test
    void getTraineeProfileWithNonExistingUsername() {
        //given()
        String username = "Vasiliy.Smith";
        //when
        when(traineeDao.findByUsername(any())).thenReturn(Optional.empty());
        //then
        Exception ex = assertThrows(UsernameNotFoundException.class, () -> traineeService.getTrainee(username));
        assertEquals("User with username: Vasiliy.Smith doesn't exist", ex.getMessage());
    }

}
