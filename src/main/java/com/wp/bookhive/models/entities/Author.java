package com.wp.bookhive.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String surname;
    private int age;

    @ManyToMany(mappedBy = "authors")
    private List<Book> publishedBooks;

    public Author(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.publishedBooks = new ArrayList<>();
    }

    public Author(String name, String surname, int age, List<Book> publishedBooks) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.publishedBooks = publishedBooks;
    }
}
