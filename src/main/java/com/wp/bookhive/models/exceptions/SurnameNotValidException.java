package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "the specified surname is either null or empty")
public class SurnameNotValidException extends RuntimeException {
    public SurnameNotValidException(String surname) {
        super(String.format("Surname: %s not valid", surname));
    }
}
