package com.seitov.gym.service;

import com.seitov.gym.dao.UserDao;
import com.seitov.gym.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;
    @Test
    public void usernameGeneration() {
        //given
        String firstName = "John";
        String lastName = "Smith";
        //then
        assertEquals("John.Smith",userService.generateUsername(firstName, lastName));
    }


    @Test
    public void usernameDuplicateGeneration() {
        //given
        String firstName = "John";
        String lastName = "Smith";
        //when
        when(userDao.findByUsername(any())).thenReturn(List.of(new User(), new User(), new User()));
        //then
        assertEquals("John.Smith3",userService.generateUsername(firstName, lastName));
    }

    @Test
    public void passwordGeneration() {
        assertEquals(10, userService.generatePassword().length());
    }



}
