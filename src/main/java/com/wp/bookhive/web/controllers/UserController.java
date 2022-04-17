package com.wp.bookhive.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user")    //dali da se stavi @PathVariable za userId?
    public String getUserViewPage() {
        return "index";
    }
}
