package com.seitov.gym.service.impl;

import com.seitov.gym.dao.TraineeDao;
import com.seitov.gym.dao.TrainerDao;
import com.seitov.gym.dto.TraineeDto;
import com.seitov.gym.dto.TrainerShortDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.dto.common.PersonalInfo;
import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.Trainer;
import com.seitov.gym.service.PasswordService;
import com.seitov.gym.service.TraineeService;
import com.seitov.gym.service.UserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UsernamePasswordDto createTrainee(PersonalInfo dto) {
        Trainee trainee = orikaMapper.map(dto, Trainee.class);
        String rawPassword = passwordService.generatePassword();
        String encodedPassword = passwordService.encodePassword(rawPassword);
        String username = userService.generateUsername(dto.getFullName().getFirstName(),
                dto.getFullName().getLastName());
        trainee.getUser().setUsername(username);
        trainee.getUser().setPassword(encodedPassword);
        trainee.getUser().setIsActive(true);
        traineeDao.create(trainee);
        return new UsernamePasswordDto(username, rawPassword);
    }

    @Override
    public TraineeDto getTrainee(String username) {
        Trainee trainee = traineeDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " doesn't exist"));
        return orikaMapper.map(trainee, TraineeDto.class);
    }

    @Override
    @Transactional
    public List<TrainerShortDto> addTrainers(String traineeUsername, List<String> usernames) {
        Trainee trainee = traineeDao.findByUsername(traineeUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + traineeUsername + " doesn't exist"));
        Set<Trainer> trainers = trainerDao.findByUsername(usernames);
        if(trainee.getTrainers() == null) {
            trainee.setTrainers(trainers);
        } else {
            trainee.getTrainers().addAll(trainers);
        }
        traineeDao.update(trainee);
        return orikaMapper.mapAsList(trainee.getTrainers(), TrainerShortDto.class);
    }

    @Override
    @Transactional
    public TraineeDto updateTrainee(String username, PersonalInfo dto) {
        Trainee trainee = traineeDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " doesn't exist"));
        String address = dto.getAddress() != null ? dto.getAddress() : trainee.getAddress();
        LocalDate dateOfBirth = dto.getDateOfBirth() != null ? dto.getDateOfBirth() : trainee.getDateOfBirth();
        trainee.getUser().setFirstName(dto.getFullName().getFirstName());
        trainee.getUser().setLastName(dto.getFullName().getLastName());
        trainee.setAddress(address);
        trainee.setDateOfBirth(dateOfBirth);
        trainee = traineeDao.update(trainee);
        return orikaMapper.map(trainee, TraineeDto.class);
    }

    @Override
    @Transactional
    public void deleteTrainee(String username) {
        Trainee trainee = traineeDao.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " doesn't exist"));
        traineeDao.delete(trainee);
    }

}