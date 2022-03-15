package com.wp.bookhive.models.entities;

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
    @ManyToMany
    private List<Author> authors;

    public Book(String isbn, String name, String description, LocalDate datePublished, List<Author> authors) {
        this.isbn = isbn;
        this.name = name;
        this.description = description;
        this.datePublished = datePublished;
        this.authors = authors;
    }

    //    private Double price;
//    private Double discount;
//    treba da bidat vo relacijata pomegju book i bookshop

//    private long cursor
//    treba da bide vo relacijata pomegju book i user

//    private List<Publisher>
//    treba da bide vo relacija many to many
//    edna kniga moze da ja izdavaat povekje izdavacki kukji
//    edna izdavacka kukja moze da izdava povekje knigi

}
