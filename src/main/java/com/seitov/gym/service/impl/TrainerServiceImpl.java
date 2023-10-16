package com.seitov.gym.service.impl;

import com.seitov.gym.dao.TrainerDao;
import com.seitov.gym.dao.TrainingTypeDao;
import com.seitov.gym.dto.TrainerDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.entity.Trainer;
import com.seitov.gym.entity.TrainingType;
import com.seitov.gym.entity.User;
import com.seitov.gym.service.PasswordService;
import com.seitov.gym.service.TrainerService;
import com.seitov.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private TrainingTypeDao trainingTypeDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;

    @Transactional
    public UsernamePasswordDto createTrainer(TrainerDto dto) {
        Trainer trainer = new Trainer();
        User user = new User();
        TrainingType trainingType = trainingTypeDao.findByName(dto.getSpecialization());
        String rawPassword = passwordService.generatePassword();
        trainingType.setName(dto.getSpecialization());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(userService.generateUsername(dto.getFirstName(), dto.getLastName()));
        user.setPassword(passwordService.encodePassword(rawPassword));
        user.setIsActive(true);
        trainer.setTrainingType(trainingType);
        trainer.setUser(user);
        trainerDao.create(trainer);
        return new UsernamePasswordDto(user.getUsername(), rawPassword);
    }

}
