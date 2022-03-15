package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "email is either null od empty string")
public class EmailNotValidException extends RuntimeException {
    public EmailNotValidException(String email) {
        super(String.format("Email: %s not valid", email));
    }
}
