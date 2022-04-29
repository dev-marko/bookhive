package com.wp.bookhive.models.entities;

import com.wp.bookhive.models.enums.Genres;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String isbn;
    private String name;
    private String description;
    private LocalDate datePublished;
    private String coverImageUrl;

    @ElementCollection
    private List<Genres> genres;

    @ManyToMany
    private List<Author> authors;

    @OneToMany(mappedBy = "book")
    private List<UserBook> userBooks;

    public Book(String isbn, String name, String description, String ciu, LocalDate datePublished, List<Author> authors, List<Genres> genres) {
        this.isbn = isbn;
        this.name = name;
        this.description = description;
        this.coverImageUrl = ciu;
        this.datePublished = datePublished;
        this.authors = authors;
        this.genres = genres;
    }

}
