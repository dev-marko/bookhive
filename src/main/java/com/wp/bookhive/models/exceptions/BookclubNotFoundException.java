package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "bookclub not found")
public class BookclubNotFoundException extends RuntimeException {
    public BookclubNotFoundException(Integer bookClubId) {
        super(String.format("Bookclub with ID: %d not found.", bookClubId));
    }
}
