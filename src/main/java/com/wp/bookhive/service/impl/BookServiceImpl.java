package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.Author;
import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.enums.Genres;
import com.wp.bookhive.models.exceptions.BookNotFoundException;
import com.wp.bookhive.models.pages.BookPage;
import com.wp.bookhive.repository.AuthorRepository;
import com.wp.bookhive.repository.BookRepository;
import com.wp.bookhive.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Page<Book> findByPage(BookPage bookPage) {
        Sort sort = Sort.by(bookPage.getSortDirection(), bookPage.getSortBy());
        Pageable pageable = PageRequest.of(
                bookPage.getCurrentPage(),
                bookPage.getPageSize(),
                sort);
        return this.bookRepository.findAllByIsValidTrue(pageable);
    }

    @Override
    public Book findById(Integer id) {
        return this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Book findByIsbn(String isbn) {
        return this.bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
    }

    @Override
    public Book add(String isbn, String name, String description, String ciu, LocalDate datePublished, List<Integer> authorIds, List<Genres> genres) {
        List<Author> authors = this.authorRepository.findAllById(authorIds);
        return this.bookRepository.save(new Book(isbn, name, description, ciu, datePublished, authors, genres));
    }

    @Override
    public Book edit(Integer id, String isbn, String name, String description, String ciu, LocalDate datePublished, List<Integer> authorIds, List<Genres> genres) {
        Book book = this.findById(id);
        book.setIsbn(isbn);
        book.setName(name);
        book.setDescription(description);
        book.setCoverImageUrl(ciu);
        book.setDatePublished(datePublished);
        book.setGenres(genres);
        List<Author> authors = this.authorRepository.findAllById(authorIds);
        book.setAuthors(authors);
        return this.bookRepository.save(book);
    }

    @Override
    public void deleteById(Integer id) {
        Book book = this.findById(id);
        book.setValid(false);
        bookRepository.save(book);
    }
}
