package com.wp.bookhive.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "bookhive_user")
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String surname;
    private int age;
    private String password;
    private String email;
    private String address;
    @ManyToMany
    private List<Book> booksOwned;
    private String location;
    private String biography;
    private String interests;
    @ManyToMany(mappedBy = "members")
    private List<BookClub> bookClubs;
}
