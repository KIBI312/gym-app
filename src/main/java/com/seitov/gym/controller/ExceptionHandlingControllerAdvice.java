package com.seitov.gym.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.seitov.gym.dto.ErrorMessage;
import com.seitov.gym.entity.TrainingType;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@RestControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @Hidden
    @ExceptionHandler(value = InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorMessage handleInvalidFormatExceptions(InvalidFormatException ex) {
        ErrorMessage message = new ErrorMessage();
        message.setTimestamp(timestamp());
        if(ex.getTargetType().isAssignableFrom(TrainingType.Name.class)) {
            message.setMessage("Please select valid specialization value");
        } else if (ex.getTargetType().isAssignableFrom(LocalDate.class)) {
            message.setMessage("Date should be in format: dd-MM-yyyy");
        } else {
            message.setMessage(ex.getMessage());
        }
        return message;
    }

    @Hidden
    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorMessage handleUsernameNotFound(UsernameNotFoundException ex) {
        ErrorMessage message = new ErrorMessage();
        message.setTimestamp(timestamp());
        message.setMessage(ex.getMessage());
        return message;
    }

    private String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

}
