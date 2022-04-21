package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.BookClub;
import com.wp.bookhive.models.entities.Post;
import com.wp.bookhive.models.entities.Topic;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.repository.BookclubRepository;
import com.wp.bookhive.service.PostService;
import com.wp.bookhive.service.TopicService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;
    private final PostService postService;
    // TODO DA SE NAPRAVI BOOKCLUB SERVICE

    public TopicController(TopicService topicService, PostService postService) {
        this.topicService = topicService;
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public String getTopicById(@PathVariable Integer id, Model model) {
        Topic topic = this.topicService.findById(id);
        List<Post> topicPosts = this.postService.findByTopic(id);

        model.addAttribute("topic", topic);
        model.addAttribute("posts", topicPosts);

        return "topic";
    }

    @GetMapping("/add")
    public String getAddTopicForm(Model model) {
        return "add-topic-form";
    }

    @PostMapping("/add")
    public String addNewTopic(@RequestParam String title, @RequestParam Integer bookClubId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        this.topicService.save(title, user.getId(), bookClubId);

        return "redirect:/user";
    }
}
