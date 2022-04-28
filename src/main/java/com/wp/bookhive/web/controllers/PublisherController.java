package com.wp.bookhive.web.controllers;

import com.wp.bookhive.models.entities.Publisher;
import com.wp.bookhive.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/publisher")
@AllArgsConstructor
@Controller
public class PublisherController {

    private final PublisherService publisherService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/all")
    public String getAllPublisherShops(Model model) {
        List<Publisher> publisherList = this.publisherService.getAllPublishers();
        model.addAttribute("publishers", publisherList);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{publisherId}")
    public String getPublisher(@PathVariable Integer publisherId, Model model) {
        Publisher publisher = this.publisherService.findById(publisherId);
        model.addAttribute("publisher", publisher);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    //plus site knigi
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/edit/{publisherId}")
    public String getPublisherEditPage(@PathVariable Integer publisherId, Model model) {
        Publisher publisher = this.publisherService.findById(publisherId);
        model.addAttribute("publisher", publisher);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }

    //edit na books
    //plus knigi
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{publisherId}")
    public String deletePublisher(@PathVariable int publisherId) {
        this.publisherService.deleteById(publisherId);
        return ""; //sega za sega vaka neka stoi pa ke vidime otposle sto ke se vrakja i dali ke postoi ovaa metoda
    }
}
