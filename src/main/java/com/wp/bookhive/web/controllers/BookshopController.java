package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.BookShop;
import com.wp.bookhive.service.BookshopService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bookshops")
@AllArgsConstructor
@Controller
public class BookshopController {

    private final BookshopService bookshopService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/map")
    public String test(Model model) {
        List<BookShop> bookshopList = this.bookshopService.getAllBookshops();
        model.addAttribute("bookshops", bookshopList);
        return "bookshop_geolocation";
    }

    @GetMapping("/all")
    public String getAllBookshops(Model model) {
        List<BookShop> bookshopList = this.bookshopService.getAllBookshops();
        model.addAttribute("bookshops", bookshopList);
        model.addAttribute("bookshops_selected", true);
        model.addAttribute("bodyContent", "bookshops");
        return "index";
    }

    @PostMapping("/search")
    public String getAllBookshops(@RequestParam(required = false) String search, Model model) {
        List<BookShop> bookshopList = null;
        if(search!=null) bookshopList = this.bookshopService.searchBookshops(search);
        else bookshopList = this.bookshopService.getAllBookshops();
        model.addAttribute("bookshops", bookshopList);
        model.addAttribute("bookshops_selected", true);
        model.addAttribute("bodyContent", "bookshops");
        return "index";
    }

    @GetMapping("/{bookshopId}")
    public String getBookshop(@PathVariable Integer bookshopId, Model model) {
        BookShop bookshop = this.bookshopService.findById(bookshopId);
        model.addAttribute("bookshop", bookshop);
        return "bookshop_bio";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //plus site knigi
    @GetMapping("/edit/{bookshopId}")
    public String getBookshopEditPage(@PathVariable Integer bookshopId, Model model) {
        BookShop bookshop = this.bookshopService.findById(bookshopId);
        model.addAttribute("bookshop", bookshop);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //edit na bookshop info
    @PostMapping()
    public String saveBookshop(@RequestParam(required = false) Integer bookshopId,
                               @RequestParam String address,
                               @RequestParam String city,
                               @RequestParam String name,
                               @RequestParam String bookshopEmail,
                               @RequestParam String phoneNumber,
                               @RequestParam String webSiteLink,
                               @RequestParam String latitude,
                               @RequestParam String longitude,
                               Model model) {
        if (bookshopId != null) {
            this.bookshopService.edit(bookshopId, address, city, name, bookshopEmail, phoneNumber, webSiteLink, latitude, longitude);
        } else {
            this.bookshopService.save(address, city, name, bookshopEmail, phoneNumber, webSiteLink, latitude, longitude);
        }
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/edit/{bookshopId}/add-book")
    public String getAddBookForm(@PathVariable Integer bookshopId, Model model) {
        BookShop bookshop = this.bookshopService.findById(bookshopId);
        model.addAttribute("bookshop", bookshop);
        return ""; //blagoj view
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    //edit na bookshop books
    @PostMapping("/edit/{bookshopId}/add-book")
    public String addBook(@PathVariable Integer bookshopId,
                          @RequestParam String isbn,
                          @RequestParam String name,
                          @RequestParam String description,
                          @RequestParam String datePublished,
                          Model model) {
        //lista od avtori
        //this.bookshopService.addBook(bookshopId, isbn, name, description, datePublished);
        return ""; //redirect kon @GetMapping("/{bookshopId}")
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{bookshopId}")
    public String deleteBookshop(@PathVariable int bookshopId) {
        this.bookshopService.deleteById(bookshopId);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }
}
