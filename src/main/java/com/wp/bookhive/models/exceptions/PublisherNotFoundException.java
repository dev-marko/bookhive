package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "there is no publisher with that id")
public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException(Integer id) {
        super(String.format("Publisher with: %d not found", id));
    }
}