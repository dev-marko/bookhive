package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.config.oauth2.CustomOAuth2User;
import com.wp.bookhive.models.entities.Author;
import com.wp.bookhive.models.entities.Invitation;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.service.InvitationService;
import com.wp.bookhive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final InvitationService invitationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping()    //dali da se stavi @PathVariable za userId?
    public String getUserViewPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        List<Invitation> invitations = this.invitationService.findByReceiver(user.getEmail());

        if(!invitations.isEmpty()) {
            model.addAttribute("invitations", invitations);
        }

        model.addAttribute("bodyContent", "landing-page");
        model.addAttribute("home_selected", true);

        return "index";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/profile")
    public String getMyProfilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "my-profile");
        return "index";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/profile/edit")
    public String editUser(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String confirmPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }
        userService.update(user,name,surname,age,address,email,password,confirmPassword);

        return "redirect:/user";
    }
}
