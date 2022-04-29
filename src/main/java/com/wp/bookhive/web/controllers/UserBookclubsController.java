package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.config.oauth2.CustomOAuth2User;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.service.BookclubService;
import com.wp.bookhive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/my-bookclubs")
@AllArgsConstructor
public class UserBookclubsController {

    private BookclubService bookclubService;
    private UserService userService;

    @GetMapping
    public String getMyBookclubs(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        model.addAttribute("bookClubs", this.bookclubService.findByMember(user.getId()));
        model.addAttribute("loggedIn", user);
        model.addAttribute("mybookclubs_selected", true);
        model.addAttribute("bodyContent", "my-bookclubs");

        return "index";
    }
}
