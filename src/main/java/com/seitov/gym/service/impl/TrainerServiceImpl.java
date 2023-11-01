package com.seitov.gym.service.impl;

import com.seitov.gym.dao.TrainerDao;
import com.seitov.gym.dao.TrainingTypeDao;
import com.seitov.gym.dto.TrainerDto;
import com.seitov.gym.dto.TrainerShortDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.entity.Trainer;
import com.seitov.gym.entity.TrainingType;
import com.seitov.gym.entity.User;
import com.seitov.gym.service.PasswordService;
import com.seitov.gym.service.TrainerService;
import com.seitov.gym.service.UserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

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
    @Autowired
    private MapperFacade orikaMapper;

    @Override
    @Transactional
    public UsernamePasswordDto createTrainer(TrainerShortDto dto) {
        TrainingType trainingType = trainingTypeDao.findByName(dto.getSpecialization());
        String rawPassword = passwordService.generatePassword();
        String encodedPassword = passwordService.encodePassword(rawPassword);
        User user = new User();
        Trainer trainer = new Trainer();
        user.setFirstName(dto.getFullName().getFirstName());
        user.setLastName(dto.getFullName().getLastName());
        user.setUsername(userService.generateUsername(user.getFirstName(), user.getLastName()));
        user.setPassword(encodedPassword);
        user.setIsActive(true);
        trainer.setTrainingType(trainingType);
        trainer.setUser(user);
        trainer.setTrainees(Collections.emptySet());
        trainerDao.create(trainer);
        return new UsernamePasswordDto(user.getUsername(), rawPassword);
    }

    @Override
    public TrainerDto getTrainer(String username) {
        Trainer trainer = trainerDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " doesn't exist"));
        return orikaMapper.map(trainer, TrainerDto.class);
    }

    @Override
    @Transactional
    public TrainerDto updateTrainer(String username, TrainerShortDto dto) {
        Trainer trainer = trainerDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " doesn't exist"));
        TrainingType trainingType = trainingTypeDao.findByName(dto.getSpecialization());
        trainer.getUser().setFirstName(dto.getFullName().getFirstName());
        trainer.getUser().setLastName(dto.getFullName().getLastName());
        trainer.setTrainingType(trainingType);
        trainer = trainerDao.update(trainer);
        return orikaMapper.map(trainer, TrainerDto.class);
    }

}
