package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.User;

import java.util.List;

public interface UserBooksService {
    List<Book> getMyBooks(Integer userId);
    List<Book> getMyWishlist(Integer userId);
    void addBookToMyBooks(Integer userId, Book book);
    void addBookToMyWishlist(Integer userId, Book book);
    void removeBookFromMyBooks(Integer userId, Book book);
    void removeBookFromMyWishlist(Integer userId, Book book);
}
