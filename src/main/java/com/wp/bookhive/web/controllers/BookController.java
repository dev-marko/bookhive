package com.wp.bookhive.web.controllers;


import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.enums.Genres;
import com.wp.bookhive.models.pages.BookPage;
import com.wp.bookhive.repository.BookshopRepository;
import com.wp.bookhive.service.AuthorService;
import com.wp.bookhive.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookshopRepository bookshopRepository;

    public BookController(BookService bookService, AuthorService authorService, BookshopRepository bookshopRepository) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookshopRepository = bookshopRepository;
    }

    @GetMapping
    public String getAllBooks(@RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              Model model) {

        int currentPage = page.orElse(0);
        int pageSize = size.orElse(8);

        BookPage bookPage = new BookPage();
        bookPage.setCurrentPage(currentPage);
        bookPage.setPageSize(pageSize);

        Page<Book> bookPageToDisplay = this.bookService.findByPage(bookPage);
        model.addAttribute("books", bookPageToDisplay);

        int totalPages = bookPageToDisplay.getTotalPages();
        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("bodyContent", "books");
        model.addAttribute("books_selected", true);

        return "index";
    }

    @GetMapping("/form")
    public String getAddBook(Model model){
        model.addAttribute("authors", this.authorService.findAll());
        model.addAttribute("genres", Genres.values());
        return "book";
    }

    @PostMapping("/add")
    public String saveBook(
            @RequestParam(required = false) Integer id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String isbn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePublished,
            @RequestParam List<Integer> authors,
            @RequestParam List<Genres> genres

    ){

        if (id != null) {
            this.bookService.edit(id, isbn, name, description, datePublished, authors, genres);
        } else {
            this.bookService.add(isbn, name, description, datePublished, authors, genres);
        }

        return "redirect:/books";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/edit")
    public String getEditBook(@PathVariable Integer id, Model model) throws Exception {
        model.addAttribute("book", this.bookService.findById(id));
        model.addAttribute("authors", this.authorService.findAll());
        model.addAttribute("genres", Genres.values());
        return "book";
    }
    @GetMapping("/{id}/view")
    public String getViewBook(@PathVariable Integer id, Model model) throws Exception {
        Book book = this.bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", book.getAuthors().stream().map(x -> x.getName() + " " + x.getSurname()).collect(Collectors.toList()));
        model.addAttribute("bookshops", this.bookshopRepository.getAllByBooks(book));
        return "book_bio";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable Integer id){
        this.bookService.deleteById(id);
        // TODO: delete button on the book cards
        return "redirect:/books";
    }
}
