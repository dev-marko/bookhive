package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "latitude or longitude properties are either empty or null")
public class LatOrLotNotValidException extends RuntimeException {
    public LatOrLotNotValidException(String latitude,String longitude) {
        super(String.format("Lat: %s or Lot %s not valid", latitude, longitude));
    }
}

