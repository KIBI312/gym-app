package com.seitov.gym.service.impl;

import com.seitov.gym.dao.TraineeDao;
import com.seitov.gym.dao.TrainerDao;
import com.seitov.gym.dto.*;
import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.Trainer;
import com.seitov.gym.entity.User;
import com.seitov.gym.service.PasswordService;
import com.seitov.gym.service.TraineeService;
import com.seitov.gym.service.UserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private MapperFacade orikaMapper;

    @Override
    @Transactional
    public UsernamePasswordDto createTrainee(UserDto dto) {
        Trainee trainee = new Trainee();
        User user = new User();
        String rawPassword = passwordService.generatePassword();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(userService.generateUsername(dto.getFirstName(), dto.getLastName()));
        user.setPassword(passwordService.encodePassword(rawPassword));
        user.setIsActive(true);
        trainee.setDateOfBirth(dto.getDateOfBirth());
        trainee.setAddress(dto.getAddress());
        trainee.setUser(user);
        traineeDao.create(trainee);
        return new UsernamePasswordDto(user.getUsername(), rawPassword);
    }

    @Override
    public TraineeDto getTrainee(String username) {
        Trainee trainee = traineeDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " doesn't exist"));
        TraineeDto traineeDto =new TraineeDto();
        traineeDto.setFirstName(trainee.getUser().getFirstName());
        traineeDto.setLastName(trainee.getUser().getLastName());
        traineeDto.setDateOfBirth(trainee.getDateOfBirth());
        traineeDto.setAddress(trainee.getAddress());
        traineeDto.setIsActive(trainee.getUser().getIsActive());
        traineeDto.setTrainers(orikaMapper.mapAsList(trainee.getTrainers(), TrainerDto.class));
        return traineeDto;
    }

    @Override
    @Transactional
    public List<TrainerDto> addTrainers(String traineeUsername, List<String> usernames) {
        Trainee trainee = traineeDao.findByUsername(traineeUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + traineeUsername + " doesn't exist"));
        Set<Trainer> trainers = trainerDao.findByUsername(usernames);
        if(trainee.getTrainers() == null) {
            trainee.setTrainers(trainers);
        } else {
            trainee.getTrainers().addAll(trainers);
        }
        traineeDao.update(trainee);
        return orikaMapper.mapAsList(trainee.getTrainers(), TrainerDto.class);
    }

    @Override
    @Transactional
    public TraineeDto updateTrainee(UpdateTraineeDto dto) {
        Trainee trainee = traineeDao.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + dto.getUsername() + " doesn't exist"));
        if(!passwordService.matches(dto.getPassword(), trainee.getUser().getPassword()) && trainee.getUser().getIsActive()){
            throw new AccessDeniedException("Incorrect credentials for trainee update");
        }
        String address = dto.getAddress() != null ? dto.getAddress() : trainee.getAddress();
        LocalDate dateOfBirth = dto.getDateOfBirth() != null ? dto.getDateOfBirth() : trainee.getDateOfBirth();
        trainee.getUser().setFirstName(dto.getFirstName());
        trainee.getUser().setLastName(dto.getLastName());
        trainee.setAddress(address);
        trainee.setDateOfBirth(dateOfBirth);
        trainee = traineeDao.update(trainee);
        return orikaMapper.map(trainee, TraineeDto.class);
    }

}