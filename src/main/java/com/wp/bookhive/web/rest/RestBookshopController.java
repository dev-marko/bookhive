package com.wp.bookhive.web.rest;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.BookShop;
import com.wp.bookhive.repository.BookRepository;
import com.wp.bookhive.repository.BookshopRepository;
import com.wp.bookhive.service.BookService;
import com.wp.bookhive.service.BookshopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/bookshops")
public class RestBookshopController {
    private final BookshopService bookshopService;
    private final BookService bookService;

    public RestBookshopController(BookshopService bookshopService, BookService bookService) {
        this.bookshopService = bookshopService;
        this.bookService = bookService;
    }

    @GetMapping("/{bookId}")
    List<BookShop> getAllBookshops(@PathVariable Integer bookId){
        Book book = bookService.findById(bookId);
        return bookshopService.findAllByBook(book);
    }
}
