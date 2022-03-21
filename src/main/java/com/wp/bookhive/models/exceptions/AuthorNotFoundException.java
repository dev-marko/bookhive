package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "author not found")
public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Integer id) {
        super(String.format("Author with ID: %d not found.", id));
    }

    public AuthorNotFoundException(String name, String surname) {
        super(String.format("Author with name or surname: %s %s not found.", name, surname));
    }
}
