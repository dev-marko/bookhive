package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "book not found")
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Integer id) {
        super(String.format("Book with ID: %d doesn't exists", id));
    }

    public BookNotFoundException(String isbn) {
        super(String.format("Book with ISBN: %s doesn't exists", isbn));
    }

}
