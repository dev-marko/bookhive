package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "the specified name is either null or empty")
public class NameNotValidException extends RuntimeException {
    public NameNotValidException(String name) {
        super(String.format("Name: %s not valid", name));
    }
}
