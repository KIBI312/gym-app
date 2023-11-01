package com.seitov.gym.config;

import com.seitov.gym.dto.common.FullName;
import com.seitov.gym.dto.common.PersonalInfo;
import com.seitov.gym.entity.Trainee;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Import(OrikaConfig.class)
public class OrikaConfigTest {

    @Autowired
    private MapperFacade orikaMapper;

    @Test
    void personalInfoToTrainee() {
        //given
        PersonalInfo personalInfo = new PersonalInfo();
        FullName fullName = new FullName("John", "Smith");
        personalInfo.setFullName(fullName);
        personalInfo.setAddress("NY street");
        personalInfo.setDateOfBirth(LocalDate.of(1975,3,21));
        //then
        Trainee trainee = orikaMapper.map(personalInfo, Trainee.class);
        assertEquals("John", trainee.getUser().getFirstName());
        assertEquals("Smith", trainee.getUser().getLastName());
        assertEquals(LocalDate.of(1975,3,21), trainee.getDateOfBirth());
        assertEquals("NY street", trainee.getAddress());
    }

}
