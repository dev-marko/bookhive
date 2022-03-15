package com.wp.bookhive.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "no id for bookshop")
public class BookshopNotFoundException extends RuntimeException {
    public BookshopNotFoundException(Integer bookshopId) {
        super(String.format("Bookshop with id: %bookshopId doesn't exists", bookshopId));
    }
}
