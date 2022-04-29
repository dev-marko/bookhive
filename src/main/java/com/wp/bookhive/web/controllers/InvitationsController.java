package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.config.oauth2.CustomOAuth2User;
import com.wp.bookhive.models.entities.Invitation;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.service.BookclubService;
import com.wp.bookhive.service.InvitationService;
import com.wp.bookhive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class InvitationsController {

    private final InvitationService invitationService;
    private final UserService userService;
    private final BookclubService bookclubService;

    @PostMapping("/invite")
    public String sendInvitation(@RequestParam Integer bookClubId,
                                 @RequestParam Integer senderId,
                                 @RequestParam String receiverEmail,
                                 @RequestParam String message) {

        this.invitationService.save(senderId, receiverEmail, bookClubId, message, false);
        return "redirect:/bookclubs/" + bookClubId;
    }

    @GetMapping("/invitations")
    public String getMyInvitations(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        List<Invitation> invitations = this.invitationService.findByReceiver(user.getEmail());
        model.addAttribute("invitations", invitations);
        model.addAttribute("invitations_selected", true);
        model.addAttribute("bodyContent", "invitations-all");

        return "index";
    }

    @PostMapping("/invitation/{id}/accept")
    public String acceptInvitation(@PathVariable Integer id) {
        Invitation invitation = this.invitationService.findById(id);
        this.bookclubService.addUserToBookclub(invitation.getBookClub().getId(), invitation.getReceiver().getId());
        this.invitationService.deleteById(id);

        return "redirect:/invitations";
    }

    @DeleteMapping("/invitation/{id}/decline")
    public String declineInvitation(@PathVariable Integer id) {
        this.invitationService.deleteById(id);

        return "redirect:/invitations";
    }

    @PostMapping("/request/{id}/accept")
    public String acceptRequest(@PathVariable Integer id) {
        Invitation invitation = this.invitationService.findById(id);
        this.bookclubService.addUserToBookclub(invitation.getBookClub().getId(), invitation.getSender().getId());
        this.invitationService.deleteById(id);

        return String.format("redirect:/bookclubs/%d/requests", invitation.getBookClub().getId());
    }

    @DeleteMapping("/request/{id}/decline")
    public String declineRequest(@PathVariable Integer id) {
        this.invitationService.deleteById(id);

        return "redirect:/bookclubs";
    }
}
