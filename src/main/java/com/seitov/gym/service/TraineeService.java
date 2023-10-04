package com.seitov.gym.service;

import com.seitov.gym.dao.TraineeDao;
import com.seitov.gym.dto.UserDto;
import com.seitov.gym.dto.UsernamePasswordDto;
import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeService {

    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private UserService userService;

    public UsernamePasswordDto createTrainee(UserDto dto) {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(userService.generateUsername(dto.getFirstName(), dto.getLastName()));
        user.setPassword(userService.generatePassword());
        user.setIsActive(true);
        trainee.setDateOfBirth(dto.getDateOfBirth());
        trainee.setAddress(dto.getAddress());
        trainee.setUser(user);
        traineeDao.create(trainee);
        return new UsernamePasswordDto(user.getUsername(), user.getPassword());
    }

}
