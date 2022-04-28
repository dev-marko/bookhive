package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "invitation not found")
public class InvitationNotFoundException extends RuntimeException {
    public InvitationNotFoundException(Integer id) {
        super(String.format("Invitation with ID: %d not found.", id));
    }
}
