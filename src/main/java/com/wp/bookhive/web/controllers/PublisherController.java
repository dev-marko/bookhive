package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.Publisher;
import com.wp.bookhive.service.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/publisher")
@Controller
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/all")
    public String getAllPublisherShops(Model model) {
        List<Publisher> publisherList = this.publisherService.getAllPublishers();
        model.addAttribute("publishers", publisherList);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    @GetMapping("/{publisherId}")
    public String getPublisher(@PathVariable Integer publisherId, Model model) {
        Publisher publisher = this.publisherService.findById(publisherId);
        model.addAttribute("publisher", publisher);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    //plus site knigi
    @GetMapping("/edit/{publisherId}")
    public String getPublisherEditPage(@PathVariable Integer publisherId, Model model) {
        Publisher publisher = this.publisherService.findById(publisherId);
        model.addAttribute("publisher", publisher);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    //edit na books
    //plus knigi
    @PostMapping()
    public String savePublisher(@RequestParam(required = false) Integer publisherId,
                                @RequestParam String name,
                                @RequestParam String address,
                                @RequestParam String email,
                                @RequestParam String webSiteLink,
                                @RequestParam String phoneNumber,
                                Model model) {
        if (publisherId != null) {
            this.publisherService.edit(publisherId, name, address, email, phoneNumber, webSiteLink);
        } else {
            this.publisherService.save(name, address, email, phoneNumber, webSiteLink);
        }
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    @DeleteMapping("/{publisherId}")
    public String deletePublisher(@PathVariable int publisherId) {
        this.publisherService.deleteById(publisherId);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }
}
