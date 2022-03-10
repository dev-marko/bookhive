package com.wp.bookhive.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @GetMapping
    public String register(){
        return "register";
    }
    @PostMapping
    public String postRegister(){
        return "redirect:/login";
    }
}
