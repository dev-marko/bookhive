package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.exceptions.UserNotFoundException;
import com.wp.bookhive.repository.UserRepository;
import com.wp.bookhive.service.UserBooksService;
import org.springframework.stereotype.Service;

@Service
public class UserBooksServiceImpl implements UserBooksService {

    private final UserRepository userRepository;

    public UserBooksServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addBookToMyBooks(Integer userId, Book book) {
        User user = this.userRepository.findByIdEagerBooks(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.getBooksOwned().add(book);
        this.userRepository.save(user);
    }

    @Override
    public void addBookToMyWishlist(Integer userId, Book book) {
        User user = this.userRepository.findByIdEagerBooks(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.getWishlist().add(book);
        this.userRepository.save(user);
    }

    @Override
    public void removeBookFromMyBooks(Integer userId, Book book) {
        User user = this.userRepository.findByIdEagerBooks(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.getBooksOwned().remove(book);
        this.userRepository.save(user);
    }

    @Override
    public void removeBookFromMyWishlist(Integer userId, Book book) {
        User user = this.userRepository.findByIdEagerBooks(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.getWishlist().remove(book);
        this.userRepository.save(user);
    }
}
