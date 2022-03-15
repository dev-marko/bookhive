package com.wp.bookhive.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
public class Book {
    private String name;
    private String Description;
    @Id
    private String isbn;
    private LocalDate datePublished;
    @ManyToMany
    private List<Author> authors;

    public Book(String name, String description, String isbn, LocalDate datePublished, List<Author> authors) {
        this.name = name;
        Description = description;
        this.isbn = isbn;
        this.datePublished = datePublished;
        this.authors = authors;
    }

    //    private Double price;
//    private Double discount;
//    treba da bidat vo relacijata pomegju book i bookshop

//    private long cursor
//    treba da bide vo relacijata pomegju book i user

}
