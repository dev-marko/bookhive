package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "there is no admin with provided id")
public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(Integer id) {
        super(String.format("Admin with id: %d is not found", id));
    }
}