package com.seitov.gym.service.impl;

import com.seitov.gym.dao.UserDao;
import com.seitov.gym.entity.User;
import com.seitov.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String generateUsername(String firstName, String lastName) {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName)
                .append(".")
                .append(lastName);
        List<User> users = userDao.findByUsernameStartingWith(sb.toString());
        int usersSize = users.size();
        if(usersSize == 0) {
            return sb.toString();
        } else {
            return sb.append(users.size()).toString();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username:" + username + " doesn't exist"));
    }

}
