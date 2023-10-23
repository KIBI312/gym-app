package com.seitov.gym.dao;

import com.seitov.gym.DatabaseContainer;
import com.seitov.gym.entity.Trainee;
import com.seitov.gym.entity.User;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/sql/data.sql")
class AbstractJpaDaoTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = DatabaseContainer.getInstance();

    @Autowired
    TraineeDao traineeDao;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        trainee = new Trainee();
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setPassword("password123");
        user.setIsActive(true);
        trainee.setId(2);
        trainee.setDateOfBirth(LocalDate.of(2000, Month.APRIL, 20));
        trainee.setAddress("SomeStreet");
        trainee.setUser(user);
        trainee.setTrainers(Collections.emptySet());
    }

    @Test
    void testFindById()  {
        assertEquals(trainee, traineeDao.findById(2));
    }

    @Test
    void testFindAll() {
        assertEquals(List.of(trainee), traineeDao.findAll());
    }

    @Test
    void testEntityCreation() {
        //given
        User user = new User();
        user.setUsername("testUser");
        trainee.setId(null);
        trainee.setUser(user);
        //then
        traineeDao.create(trainee);
        assertNotNull(trainee.getId());
        assertEquals(trainee, traineeDao.findById(trainee.getId()));
    }

    @Test
    void testEntityDeletion() {
        traineeDao.delete(trainee);
        assertNull(traineeDao.findById(2));
    }

    @Test
    void testEntityDeletionById() {
        traineeDao.deleteById(2);
        assertNull(traineeDao.findById(2));
    }

    @Test
    void testEntityUpdate() {
        User user = trainee.getUser();
        user.setUsername("newUsername");
        trainee.setAddress("newAddress");
        trainee.setUser(user);
        Trainee updated = traineeDao.update(trainee);
        assertEquals("newAddress", updated.getAddress());
        assertEquals("newUsername", updated.getUser().getUsername());
    }

}

