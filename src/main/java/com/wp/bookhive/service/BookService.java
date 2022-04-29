package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.enums.Genres;
import com.wp.bookhive.models.pages.BookPage;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    List<Book> findAll();
    Page<Book> findByPage(BookPage bookPage);
    Book findById(Integer id);
    Book findByIsbn(String isbn);
    Book add(String isbn, String name, String description, String ciu, LocalDate datePublished, List<Integer> authorIds, List<Genres> genres);
    Book edit(Integer id, String isbn, String name, String description, String ciu, LocalDate datePublished, List<Integer> authorIds, List<Genres> genres);
    void deleteById(Integer id);
}
