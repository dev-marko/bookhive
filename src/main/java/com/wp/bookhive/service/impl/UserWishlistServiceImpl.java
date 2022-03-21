package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.exceptions.BookNotFoundException;
import com.wp.bookhive.models.exceptions.UserNotFoundException;
import com.wp.bookhive.repository.BookRepository;
import com.wp.bookhive.repository.UserRepository;
import com.wp.bookhive.service.UserWishlistService;
import org.springframework.stereotype.Service;

@Service
public class UserWishlistServiceImpl implements UserWishlistService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public UserWishlistServiceImpl(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBookToMyWishlist(Integer id, Integer bookId) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        user.getWishlist().add(book);
    }

    @Override
    public void removeBookFromWishlist(Integer id, Integer bookId) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        user.getWishlist().remove(book);
    }
}
