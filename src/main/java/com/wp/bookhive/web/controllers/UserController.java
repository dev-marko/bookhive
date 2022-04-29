package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.Invitation;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.service.InvitationService;
import com.wp.bookhive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping()
    public String getUserViewPage(Model model) {
        User user = userService.getAuthenticatedUser();

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
        User user = userService.getAuthenticatedUser();
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
        User user = userService.getAuthenticatedUser();
        userService.update(user,name,surname,age,address,email,password,confirmPassword);
        return "redirect:/user";
    }
}
