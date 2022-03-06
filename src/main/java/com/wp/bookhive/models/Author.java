package com.wp.bookhive.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;
@Data
@Entity
@NoArgsConstructor
public class Author {
    @Id
    private int id;
    private String name;
    private String surname;
    private int age;
    @ManyToMany(mappedBy = "authors")
    private List<Book> publishedBooks;
}
