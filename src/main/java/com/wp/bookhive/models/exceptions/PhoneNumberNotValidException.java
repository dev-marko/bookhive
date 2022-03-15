package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "the phone number is either null od empty")
public class PhoneNumberNotValidException extends RuntimeException {
    public PhoneNumberNotValidException(String phoneNumber) {
        super(String.format("Phone number: %s not valid", phoneNumber));
    }
}

