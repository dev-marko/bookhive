package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.BookClub;
import com.wp.bookhive.models.entities.Topic;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.service.BookclubService;
import com.wp.bookhive.service.InvitationService;
import com.wp.bookhive.service.TopicService;
import com.wp.bookhive.service.UserService;
import lombok.AllArgsConstructor;
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

    @GetMapping
    public String getAllBookclubs(Model model) {
        User user = userService.getAuthenticatedUser();
        model.addAttribute("bookClubs", this.bookclubService.findAll());
        model.addAttribute("loggedIn", user);
        model.addAttribute("bookclubs_selected", true);
        model.addAttribute("bodyContent", "bookclubs-all");
        return "index";
    }

    @PostMapping("/search")
    public String getBookclubsSearch(@RequestParam(required = false) String search, Model model) {
        User user = userService.getAuthenticatedUser();
        List<BookClub> bookClubList = null;
        if (search != null) {
            bookClubList = this.bookclubService.findAllByNameContainingIgnoreCase(search);
        } else {
            bookClubList = this.bookclubService.findAll();
        }
        model.addAttribute("bookClubs", bookClubList);
        model.addAttribute("bookclubs_selected", true);
        model.addAttribute("loggedIn", user);
        model.addAttribute("bodyContent", "bookclubs-all");
        return "index";
    }

    @GetMapping("/{id}")
    public String getBookclub(@PathVariable Integer id, Model model) {
        User user = userService.getAuthenticatedUser();
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
        User user = userService.getAuthenticatedUser();
        if (bookclubId != null) {
            this.bookclubService.edit(bookclubId, name, user.getId(), description);
        } else {
            this.bookclubService.save(name, user.getId(), description);
        }
        return "redirect:/bookclubs";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBookclub(@PathVariable Integer id) {
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
        User user = userService.getAuthenticatedUser();
        model.addAttribute("bookClubId", id);
        model.addAttribute("senderId", user.getId());
        model.addAttribute("bodyContent", "bookclub-invitation");
        return "index";
    }

    @GetMapping("/{id}/members")
    public String getBookclubMembersPage(@PathVariable Integer id, Model model) {
        User user = userService.getAuthenticatedUser();
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
        User user = userService.getAuthenticatedUser();
        BookClub bookClub = this.bookclubService.findById(id);
        model.addAttribute("bookclub", bookClub);
        model.addAttribute("requests", this.invitationService.findByBookClubAndIsRequest(id, true));
        model.addAttribute("bodyContent", "bookclub-requests");
        return "index";
    }

    @PostMapping("/{id}/request")
    public String requestBookclubMembership(@PathVariable Integer id) {
        User user = userService.getAuthenticatedUser();
        BookClub bookClub = this.bookclubService.findById(id);
        this.invitationService.save(user.getId(), bookClub.getOwner().getEmail(), id, "", true);
        return "redirect:/bookclubs";
    }
}
