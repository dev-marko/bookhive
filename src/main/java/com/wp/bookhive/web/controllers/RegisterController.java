package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.enums.Roles;
import com.wp.bookhive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.InvalidParameterException;

@Controller
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

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
    public String postRegister(@RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String repeatedPassword) {
        try {
            this.userService.register(name, surname, email, password, repeatedPassword);
            return "redirect:/";
        } catch (InvalidParameterException | BadCredentialsException e) {
            return "redirect:/register?error=" + e.getMessage();
        }

    }
}
