package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.service.BookService;
import com.wp.bookhive.service.UserBooksService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/**
 * This controller handles requests for My Books & My Wishlist for the users
 */

@Controller
public class UserBooksController {

    private final BookService bookService;
    private final UserBooksService userBooksService;

    public UserBooksController(BookService bookService, UserBooksService userBooksService) {
        this.bookService = bookService;
        this.userBooksService = userBooksService;
    }

    @GetMapping("/my-books")
    public String getMyBooksPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        List<Book> userBooks = this.userBooksService.getMyBooks(user.getId());
        model.addAttribute("books", userBooks);
        model.addAttribute("bodyContent", "my-books");
        return "index";
    }

    @GetMapping("/my-wishlist")
    public String getMyWishlistPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        List<Book> userWishlist = this.userBooksService.getMyWishlist(user.getId());
        model.addAttribute("wishlist", userWishlist);
        model.addAttribute("bodyContent", "my-wishlist");
        return "index";
    }

    @PostMapping("/my-books/add/{id}")
    public String addToMyBooks(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Book book = this.bookService.findById(id);
        this.userBooksService.addBookToMyBooks(user.getId(), book);
        return "redirect:/my-books";
    }

    @PostMapping("/my-wishlist/add/{id}")
    public String addToMyWishlist(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Book book = this.bookService.findById(id);
        this.userBooksService.addBookToMyWishlist(user.getId(), book);
        return "redirect:/my-wishlist";
    }

    @PostMapping("/my-books/remove/{id}")
    public String removeFromMyBooks(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Book book = this.bookService.findById(id);
        this.userBooksService.removeBookFromMyBooks(user.getId(), book);
        return "redirect:/my-books";
    }

    @PostMapping("/my-wishlist/remove/{id}")
    public String removeFromMyWishlist(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Book book = this.bookService.findById(id);
        this.userBooksService.removeBookFromMyWishlist(user.getId(), book);
        return "redirect:/my-wishlist";
    }
}
