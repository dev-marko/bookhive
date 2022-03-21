package com.wp.bookhive.service;

public interface UserWishlistService {
    void addBookToMyWishlist(Integer id, Integer bookId);
    void removeBookFromWishlist(Integer id, Integer bookId);
}
