package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "string city is either empty or null")
public class CityNotValidException extends RuntimeException {
    public CityNotValidException(String city) {
        super(String.format("City: %s is not valid", city));
    }
}
