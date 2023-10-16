package com.seitov.gym.service.impl;

import com.seitov.gym.dao.TraineeDao;
import com.seitov.gym.dto.UserDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.User;
import com.seitov.gym.service.PasswordService;
import com.seitov.gym.service.TraineeService;
import com.seitov.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;

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

}