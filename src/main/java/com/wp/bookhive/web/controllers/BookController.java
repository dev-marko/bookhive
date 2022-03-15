package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.Author;
import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.repository.AuthorRepository;
import com.wp.bookhive.repository.BookRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/addbook")
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public String getAddBook(Model model){
        model.addAttribute("authors", authorRepository.findAll());
        return "book";
    }

    @PostMapping
    public String postAddBook(@RequestParam String name,
                              @RequestParam String description,
                              @RequestParam String isbn,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePublished,
                              @RequestParam List<Integer> authors
    ){
        List<Author> authors1 = authorRepository.findAllById(authors);
        this.bookRepository.save(new Book(name, description, isbn, datePublished, authors1));
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getEditBook(@PathVariable String id, Model model) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(()->new Exception("Book not found"));
        model.addAttribute("book", book);
        model.addAttribute("authors", authorRepository.findAll());
        return "book";
    }

    @PostMapping("/{id}")
    public String postEditBook(
            @PathVariable String id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String isbn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePublished) throws Exception {
        Book book = this.bookRepository.findById(id).orElseThrow(()->new Exception("Book not found"));
        book.setName(name);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setDatePublished(datePublished);
        bookRepository.save(book);
        return "redirect:/";
    }
    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable String id){
        bookRepository.deleteById(id);
        return "redirect:/";
    }
}
