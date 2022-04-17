package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
    Author findById(Integer id);
    Author findByNameOrSurname(String name, String surname);
    Author add(String name, String surname, Integer age, String biography, List<Integer> publishedBooksIds);
    Author edit(Integer id, String name, String surname, Integer age, String biography, List<Integer> publishedBooksIds);
    void deleteById(Integer id);
}
