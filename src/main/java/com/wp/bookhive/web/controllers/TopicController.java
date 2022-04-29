package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.Post;
import com.wp.bookhive.models.entities.Topic;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.service.PostService;
import com.wp.bookhive.service.TopicService;
import com.wp.bookhive.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/topic")
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/{id}")
    public String getTopicById(@PathVariable Integer id, Model model) {
        Topic topic = this.topicService.findById(id);
        List<Post> topicPosts = this.postService.findByTopic(id);
        model.addAttribute("topic", topic);
        model.addAttribute("posts", topicPosts);
        model.addAttribute("bodyContent", "topic-posts");
        return "index";
    }

    @GetMapping("/add")
    public String getAddTopicForm(@RequestParam Integer bookClubId, Model model) {
        model.addAttribute("bookClubId", bookClubId);
        model.addAttribute("bodyContent", "topic-form");
        return "index";
    }

    @PostMapping("/add")
    public String addNewTopic(@RequestParam String title, @RequestParam Integer bookClubId) {
        User user = userService.getAuthenticatedUser();
        this.topicService.save(title, user.getId(), bookClubId);
        return "redirect:/bookclubs/" + bookClubId;
    }

    @PostMapping("/{id}/add-post")
    public String addNewPostToTopic(@PathVariable Integer id, String content) {
        User user = userService.getAuthenticatedUser();
        this.postService.save(content, user.getId(), id);
        return "redirect:/topic/" + id;
    }
}
