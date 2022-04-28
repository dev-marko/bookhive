package com.wp.bookhive.service.impl;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.entities.UserBook;
import com.wp.bookhive.models.exceptions.BookNotFoundException;
import com.wp.bookhive.models.exceptions.UserNotFoundException;
import com.wp.bookhive.repository.BookRepository;
import com.wp.bookhive.repository.UserBookRepository;
import com.wp.bookhive.repository.UserRepository;
import com.wp.bookhive.service.UserBooksService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserBooksServiceImpl implements UserBooksService {

    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;
    private final BookRepository bookRepository;

    @Override
    public UserBook findByUserAndBook(Integer userId, Integer bookId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        return this.userBookRepository.findByUserAndBook(user, book);
    }

    // ### USER BOOKS SERVICE METHODS START HERE ###
    @Override
    public List<UserBook> getMyBooks(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return this.userBookRepository.findByUser(user);
    }

    @Override
    public void addBookToMyBooks(Integer userId, Integer bookId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        UserBook newUserBook = new UserBook(user, book);
        this.userBookRepository.save(newUserBook);
    }

    @Override
    public UserBook editLastPageReadForBook(Integer userId, Integer bookId, Integer lastPageRead) {
        UserBook userBook = this.findByUserAndBook(userId, bookId);
        userBook.setLastPageRead(lastPageRead);
        return this.userBookRepository.save(userBook);
    }

    @Override
    public void removeBookFromMyBooks(Integer userId, Integer bookId) {
        UserBook userBook = this.findByUserAndBook(userId, bookId);
        this.userBookRepository.delete(userBook);
    }

    // ### USER WISHLIST SERVICE METHODS START HERE ###
    @Override
    public List<Book> getMyWishlist(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getWishlist();
    }

    @Override
    public void addBookToMyWishlist(Integer userId, Integer bookId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        user.getWishlist().add(book);
        this.userRepository.save(user);
    }

    @Override
    public void removeBookFromMyWishlist(Integer userId, Integer bookId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        user.getWishlist().remove(book);
        this.userRepository.save(user);
    }
}
