package com.seitov.gym.service;

import com.seitov.gym.dao.TrainerDao;
import com.seitov.gym.dao.TrainingTypeDao;
import com.seitov.gym.dto.TrainerDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.entity.TrainingType;
import com.seitov.gym.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private PasswordService passwordService;
    @Mock
    private TrainerDao trainerDao;
    @Mock
    private TrainingTypeDao trainingTypeDao;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    public void trainerCreation() {
        //given
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1);
        trainingType.setName(TrainingType.Name.fitness);
        TrainerDto trainerDto = new TrainerDto("John", "Smith", trainingType.getName());
        //when
        when(userService.generateUsername("John", "Smith")).thenReturn("John.Smith");
        when(trainingTypeDao.findByName(TrainingType.Name.fitness)).thenReturn(trainingType);
        when(passwordService.generatePassword()).thenReturn("1234567910");
        //then
        UsernamePasswordDto result = trainerService.createTrainer(trainerDto);
        assertEquals("John.Smith",result.getUsername());
        assertEquals(10, result.getPassword().length());
    }

}
