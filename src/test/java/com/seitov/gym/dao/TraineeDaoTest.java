package com.seitov.gym.dao;

import com.seitov.gym.DatabaseContainer;
import com.seitov.gym.entity.Trainee;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/sql/trainee.sql")
public class TraineeDaoTest {


    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = DatabaseContainer.getInstance();

    @Autowired
    private TraineeDao traineeDao;

    @Test
    public void findByUsername() {
        String username = "John.Smith";
        Optional<Trainee> trainee = traineeDao.findByUsername(username);
        assertTrue(trainee.isPresent());
        assertEquals(username, trainee.get().getUser().getUsername());
    }

    @Test
    public void findByUsernameNonExisting() {
        String username = "Vasiliy.Ivanin";
        Optional<Trainee> trainee = traineeDao.findByUsername(username);
        assertTrue(trainee.isEmpty());
    }

}
