package com.seitov.gym.config;

import com.seitov.gym.dto.TraineeDto;
import com.seitov.gym.dto.TrainerDto;
import com.seitov.gym.dto.common.FullName;
import com.seitov.gym.dto.common.PersonalInfo;
import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.Trainer;
import com.seitov.gym.entity.User;
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
        mapperFactory.classMap(PersonalInfo.class, Trainee.class)
                .customize(new CustomMapper<PersonalInfo, Trainee>() {
                    @Override
                    public void mapAtoB(PersonalInfo personalInfo, Trainee trainee, MappingContext context) {
                        User user = trainee.getUser() == null ? new User() : trainee.getUser();
                        user.setFirstName(personalInfo.getFullName().getFirstName());
                        user.setLastName(personalInfo.getFullName().getLastName());
                        trainee.setUser(user);
                        trainee.setAddress(personalInfo.getAddress());
                        trainee.setDateOfBirth(personalInfo.getDateOfBirth());
                    }
                }).register();
        mapperFactory.classMap(Trainer.class, TrainerDto.class)
                .customize(new CustomMapper<Trainer, TrainerDto>() {
                    @Override
                    public void mapAtoB(Trainer trainer, TrainerDto trainerDto, MappingContext context) {
                        FullName fullName = new FullName(trainer.getUser().getFirstName(), trainer.getUser().getLastName());
                        trainerDto.setUsername(trainer.getUser().getUsername());
                        trainerDto.setFullName(fullName);
                        trainerDto.setSpecialization(trainer.getTrainingType().getName());
                    }
                }).register();
        mapperFactory.classMap(Trainee.class, TraineeDto.class)
                .customize(new CustomMapper<Trainee, TraineeDto>() {
                    @Override
                    public void mapAtoB(Trainee trainee, TraineeDto traineeDto, MappingContext context) {
                        FullName fullName = new FullName(trainee.getUser().getFirstName(), trainee.getUser().getLastName());
                        PersonalInfo personalInfo = new PersonalInfo(fullName, trainee.getDateOfBirth(),trainee.getAddress());
                        traineeDto.setPersonalInfo(personalInfo);
                        traineeDto.setIsActive(trainee.getUser().getIsActive());
                    }
                }).byDefault().register();
        return mapperFactory.getMapperFacade();
    }

}
