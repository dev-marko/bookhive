package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.User;
import com.wp.bookhive.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage(){
        return "home";
    }

    @PostMapping
    public String postLogin(HttpServletRequest request, Model model) {
        User user;

        try {
            user = this.userService.login(request.getParameter("email"), request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            // TODO smeni go user_view vo tocniot redirect
            return "user_view";
        } catch (InvalidParameterException e) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", e.getMessage());
            return "home";
        }

    }


}
