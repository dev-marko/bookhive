package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.config.oauth2.CustomOAuth2User;
import com.wp.bookhive.models.entities.BookClub;
import com.wp.bookhive.models.entities.Topic;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.service.BookclubService;
import com.wp.bookhive.service.InvitationService;
import com.wp.bookhive.service.TopicService;
import com.wp.bookhive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookclubs")
@AllArgsConstructor
public class BookclubController {

    private final BookclubService bookclubService;
    private final UserService userService;
    private final TopicService topicService;
    private final InvitationService invitationService;

    // TODO search bar for all-books

    @GetMapping
    public String getAllBookclubs(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        model.addAttribute("bookClubs", this.bookclubService.findAll());
        model.addAttribute("loggedIn", user);
        model.addAttribute("bodyContent", "bookclubs-all");
        return "index";
    }

    @GetMapping("/{id}")
    public String getBookclub(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        BookClub bookClub = this.bookclubService.findById(id);
        List<Topic> topics = this.topicService.findByBookClub(id);

        model.addAttribute("bookclub", bookClub);
        model.addAttribute("topics", topics);
        model.addAttribute("loggedIn", user);
        model.addAttribute("bodyContent", "bookclub");

        return "index";
    }

    @GetMapping("/add")
    public String getAddBookclubPage(Model model) {
        model.addAttribute("bodyContent", "bookclub-form");
        return "index";
    }

    @GetMapping("/{id}/edit")
    public String getEditBookclubPage(@PathVariable Integer id, Model model) {
        BookClub bookClub = this.bookclubService.findById(id);

        model.addAttribute("bookclub", bookClub);
        model.addAttribute("bodyContent", "bookclub-form");

        return "index";
    }

    @PostMapping()
    public String saveBookclub(@RequestParam(required = false) Integer bookclubId,
                               @RequestParam String name,
                               @RequestParam String description) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        if (bookclubId != null) {
            this.bookclubService.edit(bookclubId, name, user.getId(), description);
        } else {
            this.bookclubService.save(name, user.getId(), description);
        }

        return "redirect:/bookclubs";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBookclub(@PathVariable Integer id) {
        // i tuka bi trebalo da ima proverka dali najaveniot korisnik ima privilegii da go brise klubot
        // ama nemame vreme za do tolku detali
        this.bookclubService.deleteById(id);

        return "redirect:/bookclubs";
    }

    @DeleteMapping("/{id}/remove-member")
    public String removeMemberFromBookclub(@PathVariable Integer id, @RequestParam Integer userId) {
        this.bookclubService.removeUserFromBookclub(id, userId);

        return String.format("redirect:/bookclubs/%d/members", id);
    }

    @DeleteMapping("/{id}/leave")
    public String leaveBookclub(@PathVariable Integer id, @RequestParam Integer userId) {
        this.bookclubService.removeUserFromBookclub(id, userId);

        return "redirect:/my-bookclubs";
    }

    @GetMapping("/{id}/invitation")
    public String getBookclubInvitationPage(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        model.addAttribute("bookClubId", id);
        model.addAttribute("senderId", user.getId());
        model.addAttribute("bodyContent", "bookclub-invitation");

        return "index";
    }

    @GetMapping("/{id}/members")
    public String getBookclubMembersPage(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        BookClub bookClub = this.bookclubService.findById(id);
        List<User> members = bookClub.getMembers();

        model.addAttribute("bookclub", bookClub);
        model.addAttribute("members", members);
        model.addAttribute("loggedIn", user);
        model.addAttribute("bodyContent", "bookclub-members");

        return "index";
    }

    @GetMapping("/{id}/requests")
    public String getBookclubMembershipRequestsPage(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        BookClub bookClub = this.bookclubService.findById(id);

        model.addAttribute("bookclub", bookClub);
        model.addAttribute("requests", this.invitationService.findByBookClubAndIsRequest(id, true));
        model.addAttribute("bodyContent", "bookclub-requests");

        return "index";
    }

    @PostMapping("/{id}/request")
    public String requestBookclubMembership(@PathVariable Integer id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }

        BookClub bookClub = this.bookclubService.findById(id);

        this.invitationService.save(user.getId(), bookClub.getOwner().getEmail(), id, "", true);

        return "redirect:/bookclubs";
    }
}
