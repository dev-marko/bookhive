package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "topic already exists")

public class TopicAlreadyExistsException extends RuntimeException{
    public TopicAlreadyExistsException(String name) {
        super(String.format("Topic with name: '%s' already exists.", name));
    }
}
