package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "the specified name already exists")
public class NameAlreadyExistsException extends RuntimeException {
    public NameAlreadyExistsException(String name) {
        super(String.format("Name: %s already exists", name));
    }
}