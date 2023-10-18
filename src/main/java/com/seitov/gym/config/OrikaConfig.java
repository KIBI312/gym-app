package com.seitov.gym.config;

import com.seitov.gym.dto.TraineeDto;
import com.seitov.gym.dto.TrainerDto;
import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.Trainer;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaConfig {

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Bean
    public MapperFacade orikaMapper() {
        mapperFactory.classMap(Trainer.class, TrainerDto.class)
                .customize(new CustomMapper<Trainer, TrainerDto>() {
                    @Override
                    public void mapAtoB(Trainer trainer, TrainerDto trainerDto, MappingContext context) {
                        trainerDto.setUsername(trainer.getUser().getUsername());
                        trainerDto.setFirstName(trainer.getUser().getFirstName());
                        trainerDto.setLastName(trainer.getUser().getLastName());
                        trainerDto.setSpecialization(trainer.getTrainingType().getName());
                    }
                }).register();
        mapperFactory.classMap(Trainee.class, TraineeDto.class)
                .customize(new CustomMapper<Trainee, TraineeDto>() {
                    @Override
                    public void mapAtoB(Trainee trainee, TraineeDto traineeDto, MappingContext context) {
                        traineeDto.setFirstName(trainee.getUser().getFirstName());
                        traineeDto.setLastName(trainee.getUser().getLastName());
                        traineeDto.setAddress(trainee.getAddress());
                        traineeDto.setDateOfBirth(trainee.getDateOfBirth());
                        traineeDto.setIsActive(trainee.getUser().getIsActive());
                    }
                }).byDefault().register();
        return mapperFactory.getMapperFacade();
    }

}
