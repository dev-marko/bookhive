package com.wp.bookhive.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user")    //dali da se stavi @PathVariable za userId?
    public String getUserViewPage(Model model) {
        model.addAttribute("bodyContent", "test");
        model.addAttribute("home_selected", true);
        return "index";
    }
}
