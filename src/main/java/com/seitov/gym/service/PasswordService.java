package com.seitov.gym.service;

public interface PasswordService {

    String generatePassword();
    String encodePassword(String password);
    Boolean matches(String rawPassword, String encodedPassword);

}
