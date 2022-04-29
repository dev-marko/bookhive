package com.wp.bookhive.web.rest;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.BookShop;
import com.wp.bookhive.service.BookService;
import com.wp.bookhive.service.BookshopService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rest/bookshops")
public class RestBookshopController {
    private final BookshopService bookshopService;
    private final BookService bookService;

    @GetMapping("/{bookId}")
    List<BookShop> getAllBookshops(@PathVariable Integer bookId){
        Book book = bookService.findById(bookId);
        return bookshopService.findAllByBook(book);
    }
}
