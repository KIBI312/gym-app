package com.seitov.gym.service.impl;

import com.seitov.gym.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String generatePassword() {
        int leftLimit = 48;
        int rightLimit = 122;
        int stringLength = 10;
        int specialCharsLeft1 = 57;
        int specialCharsRight1 = 65;
        int specialCharsLeft2 = 90;
        int specialCharsRight2 = 97;
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= specialCharsLeft1 || i >= specialCharsRight1)
                        && (i <= specialCharsLeft2 || i >= specialCharsRight2))
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
