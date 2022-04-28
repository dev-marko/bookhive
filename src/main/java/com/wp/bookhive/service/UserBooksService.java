package com.wp.bookhive.service;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.UserBook;

import java.util.List;

public interface UserBooksService {
    UserBook findByUserAndBook(Integer userId, Integer bookId);

    List<UserBook> getMyBooks(Integer userId);

    List<Book> getMyWishlist(Integer userId);

    void addBookToMyBooks(Integer userId, Integer bookId);

    UserBook editLastPageReadForBook(Integer userId, Integer bookId, Integer lastPageRead);

    void addBookToMyWishlist(Integer userId, Integer bookId);

    void removeBookFromMyBooks(Integer userId, Integer bookId);

    void removeBookFromMyWishlist(Integer userId, Integer bookId);
}
