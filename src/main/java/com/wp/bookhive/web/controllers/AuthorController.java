package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.Author;
import com.wp.bookhive.models.exceptions.AuthorNotFoundException;
import com.wp.bookhive.repository.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", this.authorRepository.findAll());
        model.addAttribute("bodyContent", "authors");
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
        this.authorRepository.save(new Author(name, surname, age, biography));
        return "redirect:/authors";
    }

    @GetMapping("/{id}")
    public String getEditAuthor(@PathVariable Integer id, Model model) throws Exception {
        Author author = authorRepository.findById(id).orElseThrow(()->new AuthorNotFoundException(id));
        model.addAttribute("author", author);
        return "add-author";
    }

    @GetMapping("{id}/view")
    public String getAuthorBiography(@PathVariable Integer id, Model model) throws Exception {
        Author author = authorRepository.findById(id).orElseThrow(()->new AuthorNotFoundException(id));
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
        Author author = this.authorRepository.findById(id).orElseThrow(()->new AuthorNotFoundException(id));
        author.setName(name);
        author.setSurname(surname);
        author.setAge(age);
        author.setBiography(biography);
        authorRepository.save(author);
        return "redirect:/authors";
    }


    @PostMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable Integer id){
        authorRepository.deleteById(id);
        return "redirect:/authors";
    }
}
