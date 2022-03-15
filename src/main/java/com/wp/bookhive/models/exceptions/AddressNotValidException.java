package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Email address not valid")
public class AddressNotValidException extends RuntimeException {
    public AddressNotValidException(String address) {
        super(String.format("Address: %s is not valid", address));
    }
}
