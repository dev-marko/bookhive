package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.Author;
import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.exceptions.AuthorNotFoundException;
import com.wp.bookhive.repository.AuthorRepository;
import com.wp.bookhive.repository.BookRepository;
import com.wp.bookhive.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Author findById(Integer id) {
        return this.authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Override
    public Author findByNameOrSurname(String name, String surname) {
        return this.authorRepository.findByNameOrSurnameContaining(name, surname).orElseThrow(() -> new AuthorNotFoundException(name, surname));
    }

    @Override
    public Author add(String name, String surname, Integer age, List<Integer> publishedBooksIds) {
        List<Book> books = this.bookRepository.findAllById(publishedBooksIds);
        return this.authorRepository.save(new Author(name, surname, age, books));
    }

    @Override
    public Author edit(Integer id, String name, String surname, Integer age, List<Integer> publishedBooksIds) {
        Author author = this.findById(id);

        author.setName(name);
        author.setSurname(surname);
        author.setAge(age);
        List<Book> books = this.bookRepository.findAllById(publishedBooksIds);
        author.setPublishedBooks(books);

        return this.authorRepository.save(author);
    }

    @Override
    public void deleteById(Integer id) {
        this.authorRepository.deleteById(id);
    }
}
