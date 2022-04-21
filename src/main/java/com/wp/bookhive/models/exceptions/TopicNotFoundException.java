package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "topic not found")
public class TopicNotFoundException extends RuntimeException{
    public TopicNotFoundException(Integer topicId) {
        super(String.format("Topic with ID: %d not found.", topicId));
    }
}
