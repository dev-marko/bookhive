package com.wp.bookhive.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String surname;
    private int age;
    @ManyToMany(mappedBy = "authors")
    private List<Book> publishedBooks;

    public Author(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
}
