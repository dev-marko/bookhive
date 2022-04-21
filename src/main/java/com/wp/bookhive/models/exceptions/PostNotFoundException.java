package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "post not found")
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Integer postId) {
        super(String.format("Post with ID: %d not found.", postId));
    }
}
