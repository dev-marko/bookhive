package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.enums.Roles;
import com.wp.bookhive.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.InvalidParameterException;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){

//        if (error != null && !error.isEmpty()) {
//            model.addAttribute("hasErrors", true);
//            model.addAttribute("error", error);
//        }

        //model.addAttribute("bodyContent", "register");

        return "register";
    }

    @PostMapping
    public String postRegister(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String repeatedPassword,
                               @RequestParam Roles role) {

        try {
            this.userService.register(email, password, repeatedPassword, role);
            return "redirect:/login";
        } catch (InvalidParameterException | BadCredentialsException e) {
            return "redirect: /register?error=" + e.getMessage();
        }

    }
}
