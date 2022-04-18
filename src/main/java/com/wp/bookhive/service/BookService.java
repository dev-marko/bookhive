package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.pages.BookPage;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    List<Book> findAll();
    Page<Book> findByPage(BookPage bookPage);
    Book findById(Integer id);
    Book findByIsbn(String isbn);
    Book add(String isbn, String name, String description, LocalDate datePublished, List<Integer> authorIds);
    Book edit(Integer id, String isbn, String name, String description, LocalDate datePublished, List<Integer> authorIds);
    Book editLastPage(Integer id, Integer lastPage);
    void deleteById(Integer id);
}
