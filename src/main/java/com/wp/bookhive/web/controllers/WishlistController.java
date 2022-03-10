package com.wp.bookhive.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishlistController {

    @GetMapping("/my-wishlist")
    public String getMyWishlistPage() {
        return "my-wishlist";
    }
}
