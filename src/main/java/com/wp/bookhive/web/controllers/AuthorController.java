package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.Author;
import com.wp.bookhive.repository.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/addauthor")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public String getAddAuthor() {
        return "author";
    }

    // TODO: change the default request mapping to /authors

    @PostMapping
    public String postAddAuthor(@RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam Integer age,
                                @RequestParam(required = false) String biography) {
        this.authorRepository.save(new Author(name, surname, age, biography));
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getEditAuthor(@PathVariable Integer id, Model model) throws Exception {
        Author author = authorRepository.findById(id).orElseThrow(() -> new Exception("Author not found"));
        model.addAttribute("author", author);
        return "author";
    }

    @PostMapping("/{id}")
    public String postEditAuthor(@PathVariable Integer id,
                                 @RequestParam String name,
                                 @RequestParam String surname,
                                 @RequestParam Integer age,
                                 @RequestParam(required = false) String biography) throws Exception {
        Author author = this.authorRepository.findById(id).orElseThrow(() -> new Exception("Author not found"));
        author.setName(name);
        author.setSurname(surname);
        author.setAge(age);
        author.setBiography(biography);

        authorRepository.save(author);

        // TODO: make it redirect to where all the authors are shown
        return "redirect:/addauthor";
    }


    @PostMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable Integer id) {
        authorRepository.deleteById(id);
        return "redirect:/";
    }
}
