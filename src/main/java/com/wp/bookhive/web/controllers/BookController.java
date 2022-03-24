package com.wp.bookhive.web.controllers;


import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.service.AuthorService;
import com.wp.bookhive.service.BookService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/*
24.03.2022
promeniv RequestMapping od /addbook vo /books
Treba i za Edit da se smeni vo /edit/{id}
 */

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = this.bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/addbook")
    public String getAddBook(Model model){
        model.addAttribute("authors", this.authorService.findAll());
        return "book";
    }

    @PostMapping
    public String postAddBook(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam String isbn,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePublished,
                              @RequestParam List<Integer> authors
    ){
        this.bookService.add(isbn, name, description, datePublished, authors);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getEditBook(@PathVariable Integer id, Model model) throws Exception {
        model.addAttribute("book", this.bookService.findById(id));
        model.addAttribute("authors", this.authorService.findAll());
        return "book";
    }

    @PostMapping("/{id}")
    public String postEditBook(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String isbn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePublished,
            @RequestParam List<Integer> authors) {

        this.bookService.edit(id, isbn, name, description, datePublished,authors);
        return "redirect:/";
    }
    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Integer id){
        this.bookService.deleteById(id);
        return "redirect:/";
    }
}
