package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "the typed passwords aren't matching")
public class PasswordsDoNotMatchException extends RuntimeException{
    public PasswordsDoNotMatchException(Integer adminId) {
        super(String.format("Passwords do not match for admin with id: %d", adminId));
    }
}
