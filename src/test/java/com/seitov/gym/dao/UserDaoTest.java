package com.seitov.gym.dao;

import com.seitov.gym.DatabaseContainer;
import com.seitov.gym.entity.User;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/user.sql")
public class UserDaoTest {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = DatabaseContainer.getInstance();

    @Autowired
    private UserDao userDao;

    @Test
    public void findByUsernameTest() {
        List<User> users = userDao.findByUsername("John.Smith");
        long matches = users.stream()
                .map(User::getUsername)
                .filter(s -> s.startsWith("John.Smith"))
                .count();
        assertEquals(5, matches);
    }

}
