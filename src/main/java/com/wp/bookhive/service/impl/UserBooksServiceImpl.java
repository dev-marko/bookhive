package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.exceptions.BookNotFoundException;
import com.wp.bookhive.models.exceptions.UserNotFoundException;
import com.wp.bookhive.repository.BookRepository;
import com.wp.bookhive.repository.UserRepository;
import com.wp.bookhive.service.UserBooksService;
import org.springframework.stereotype.Service;

@Service
public class UserBooksServiceImpl implements UserBooksService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public UserBooksServiceImpl(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBookToMyBooks(Integer id, Integer bookId) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        user.getBooksOwned().add(book);
    }

    @Override
    public void removeBookFromMyBooks(Integer id, Integer bookId) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        user.getBooksOwned().remove(book);
    }
}
