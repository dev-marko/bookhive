package com.wp.bookhive.models.entities;

import com.wp.bookhive.models.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookhive_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
    @Enumerated
    private Roles role = Roles.USER;
}
