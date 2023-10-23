package com.seitov.gym.dao;


import com.seitov.gym.DatabaseContainer;
import com.seitov.gym.entity.Trainer;
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

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/sql/trainer.sql")
class TrainerDaoTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = DatabaseContainer.getInstance();

    @Autowired
    private TrainerDao trainerDao;

    @Test
    void findListByUsername() {
        List<String> usernames = List.of("John.Smith1", "John.Smith2", "John.Smith3");
        Set<Trainer> trainers = trainerDao.findByUsername(usernames);
        assertEquals(3, trainers.size());
    }

}
