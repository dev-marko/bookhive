package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.config.oauth2.CustomOAuth2User;
import com.wp.bookhive.models.entities.Book;
import com.wp.bookhive.models.entities.User;
import com.wp.bookhive.models.entities.UserBook;
import com.wp.bookhive.service.UserBooksService;
import com.wp.bookhive.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * This controller handles requests for My Books & My Wishlist for the users
 */

@Controller
public class UserBooksController {

    private final UserBooksService userBooksService;
    private final UserService userService;

    public UserBooksController(UserBooksService userBooksService, UserService userService) {
        this.userBooksService = userBooksService;
        this.userService = userService;
    }

    // ### USER BOOKS ENDPOINTS START HERE ###

    @GetMapping("/my-books")
    public String getMyBooksPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if(auth.getPrincipal() instanceof CustomOAuth2User customOAuth2User) {
            user = userService.findByEmail(customOAuth2User.getEmail());
        } else {
            user = (User) auth.getPrincipal();
        }
        List<UserBook> userBooks = this.userBooksService.getMyBooks(user.getId());
        model.addAttribute("userBooks", userBooks);
        model.addAttribute("bodyContent", "my-books");
        model.addAttribute("mybooks_selected", true);
        return "index";
    }

    @PostMapping("/my-books/add/{id}")
    public String addToMyBooks(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        this.userBooksService.addBookToMyBooks(user.getId(), id);
        return "redirect:/my-books";
    }

    @PostMapping("/my-books/edit-last-page-read")
    public String editLastPageReadForBook(@RequestParam Integer bookId, @RequestParam Integer lastPage) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        this.userBooksService.editLastPageReadForBook(user.getId(), bookId, lastPage);
        return "redirect:/my-books";
    }

    @PostMapping("/my-books/remove/{id}")
    public String removeFromMyBooks(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        this.userBooksService.removeBookFromMyBooks(user.getId(), id);
        return "redirect:/my-books";
    }

    // ### USER WISHLIST ENDPOINTS START HERE ###

    @GetMapping("/my-wishlist")
    public String getMyWishlistPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        List<Book> userWishlist = this.userBooksService.getMyWishlist(user.getId());
        model.addAttribute("wishlist", userWishlist);
        model.addAttribute("bodyContent", "my-wishlist");
        model.addAttribute("mywishlist_selected", true);
        return "index";
    }

    @PostMapping("/my-wishlist/add/{id}")
    public String addToMyWishlist(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        this.userBooksService.addBookToMyWishlist(user.getId(), id);
        return "redirect:/my-wishlist";
    }

    @PostMapping("/my-wishlist/remove/{id}")
    public String removeFromMyWishlist(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        this.userBooksService.removeBookFromMyWishlist(user.getId(), id);
        return "redirect:/my-wishlist";
    }
}
