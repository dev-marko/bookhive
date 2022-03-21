package com.wp.bookhive.service;

public interface UserBooksService {
    void addBookToMyBooks(Integer id, Integer bookId);
    void removeBookFromMyBooks(Integer id, Integer bookId);
}
