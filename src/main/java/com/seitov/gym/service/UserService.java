package com.seitov.gym.service;

import com.seitov.gym.dao.UserDao;
import com.seitov.gym.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public String generateUsername(String firstName, String lastName) {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName)
                .append(".")
                .append(lastName);
        List<User> users = userDao.findByUsername(sb.toString());
        int usersSize = users.size();
        if(usersSize == 0) {
            return sb.toString();
        } else {
            return sb.append(users.size()).toString();
        }
    }

    public String generatePassword() {
        int leftLimit = 48;
        int rightLimit = 122;
        int stringLength = 10;
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
