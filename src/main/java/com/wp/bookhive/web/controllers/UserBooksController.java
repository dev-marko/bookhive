package com.wp.bookhive.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserBooksController {

    @GetMapping("/my-books")
    public String getMyBooksPage() {
        return "my-books";
    }
}
