package com.seitov.gym.service.impl;

import com.seitov.gym.dao.TraineeDao;
import com.seitov.gym.dao.TrainerDao;
import com.seitov.gym.dao.TrainingDao;
import com.seitov.gym.dto.TrainingDto;
import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.Trainer;
import com.seitov.gym.entity.Training;
import com.seitov.gym.service.TrainingService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingDao trainingDao;
    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private MapperFacade orikaMapper;

    @Override
    @Transactional
    public Training createTraining(TrainingDto dto) {
        Trainer trainer = trainerDao.findByUsername(dto.getTrainerUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + dto.getTrainerUsername() + " doesn't exist"));
        Trainee trainee = traineeDao.findByUsername(dto.getTraineeUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + dto.getTraineeUsername() + " doesn't exist"));
        Training training = new Training();
        training.setName(dto.getName());
        training.setType(trainer.getTrainingType());
        training.setDuration(dto.getDuration());
        training.setTrainingDate(dto.getDate());
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        trainingDao.create(training);
        return training;
    }

    @Override
    public List<TrainingDto> getTraineeTrainings(String username) {
        Trainee trainee = traineeDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " doesn't exist"));
        return orikaMapper.mapAsList(trainee.getTrainings(), TrainingDto.class);
    }

    @Override
    public List<TrainingDto> getTrainerTrainings(String username) {
        Trainer trainer = trainerDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " doesn't exist"));
        return orikaMapper.mapAsList(trainer.getTrainings(), TrainingDto.class);
    }


}
