package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.BookShop;
import com.wp.bookhive.service.BookshopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/bookshop")
@Controller
public class BookshopController {

    private final BookshopService bookshopService;

    public BookshopController(BookshopService bookshopService) {
        this.bookshopService = bookshopService;
    }

    @GetMapping("/map")
    public String test(Model model) {
        List<BookShop> bookshopList = this.bookshopService.getAllBookshops();
        model.addAttribute("bookshops", bookshopList);
        return "bookshop";
    }

    @GetMapping("/all")
    public String getAllBookshops(Model model) {
        List<BookShop> bookshopList = this.bookshopService.getAllBookshops();
        model.addAttribute("bookshops", bookshopList);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    @GetMapping("/{bookshopId}")
    public String getBookshop(@PathVariable Integer bookshopId, Model model) {
        BookShop bookshop = this.bookshopService.findById(bookshopId);
        model.addAttribute("bookshop", bookshop);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    //plus site knigi
    @GetMapping("/edit/{bookshopId}")
    public String getBookshopEditPage(@PathVariable Integer bookshopId, Model model) {
        BookShop bookshop = this.bookshopService.findById(bookshopId);
        model.addAttribute("bookshop", bookshop);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

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

    @GetMapping("/edit/{bookshopId}/add-book")
    public String getAddBookForm(@PathVariable Integer bookshopId, Model model) {
        BookShop bookshop = this.bookshopService.findById(bookshopId);
        model.addAttribute("bookshop", bookshop);
        return ""; //blagoj view
    }

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

    @DeleteMapping("/{bookshopId}")
    public String deleteBookshop(@PathVariable int bookshopId) {
        this.bookshopService.deleteById(bookshopId);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }
}
