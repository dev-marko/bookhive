package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.Author;
import com.wp.bookhive.models.exceptions.AuthorNotFoundException;
import com.wp.bookhive.repository.AuthorRepository;
import com.wp.bookhive.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", this.authorService.findAll());
        model.addAttribute("bodyContent", "authors");
        model.addAttribute("authors_selected", true);
        return "index";
    }

    @GetMapping("/add")
    public String getAddAuthor(){
        return "add-author";
    }

    @PostMapping
    public String postAddAuthor(@RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam Integer age,
                                @RequestParam(required = false) String biography){
        this.authorService.add(name, surname, age, biography);
        return "redirect:/authors";
    }

    @GetMapping("/{id}")
    public String getEditAuthor(@PathVariable Integer id, Model model) throws Exception {
        Author author = authorService.findById(id);
        model.addAttribute("author", author);
        return "add-author";
    }

    @GetMapping("{id}/view")
    public String getAuthorBiography(@PathVariable Integer id, Model model) throws Exception {
        Author author = authorService.findById(id);
        model.addAttribute("author", author);
        return "author_bio";
    }

    @PostMapping("/{id}")
    public String postEditAuthor(
                                @PathVariable Integer id,
                                @RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam Integer age,
                                @RequestParam(required = false) String biography) throws Exception {
        Author author = this.authorService.findById(id);
        authorService.edit(author.getId(),name,surname,age,biography);
        return "redirect:/authors";
    }

    @PostMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable Integer id){
        authorService.deleteById(id);
        return "redirect:/authors";
    }
}
