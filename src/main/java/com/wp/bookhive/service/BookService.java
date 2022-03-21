package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findById(Integer id);
    Book findByIsbn(String isbn);
    Book add(String isbn, String name, String description, LocalDate datePublished, List<Integer> authorIds);
    Book edit(Integer id, String isbn, String name, String description, LocalDate datePublished, List<Integer> authorIds);
    void deleteById(Integer id);
}
